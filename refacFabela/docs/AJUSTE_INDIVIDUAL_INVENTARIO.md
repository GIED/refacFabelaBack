# Implementación de Ajuste Individual de Productos en Inventario

## 📋 Resumen de Cambios

Se implementó un sistema de ajuste individual de productos con diferencias antes de la autorización final del inventario. Esto permite un control más granular y seguro del proceso de ajuste de inventario.

## 🔄 Flujo de Trabajo Actualizado

### 1. **Usuario de Almacén (ROLE_ALMACEN)**
   - Inicia inventario por ubicación
   - Captura conteos físicos
   - Cierra el inventario → Pasa a estado `EN_REVISION`

### 2. **Administrador (ROLE_ADMIN)**
   - Accede a "Autorización de Inventarios"
   - Revisa la lista de inventarios pendientes
   - Hace clic en "Revisar" para ver el detalle
   - **NUEVO:** Para cada producto con diferencia:
     - Hace clic en botón "Ajustar"
     - Ingresa el motivo del ajuste
     - Confirma → Se actualiza `tw_productobodega`
     - El producto queda marcado como "Ajustado"
   - Una vez que **TODOS** los productos con diferencias estén ajustados:
     - El botón "Autorizar Inventario" se habilita
     - Puede proceder con la autorización final

## 🗄️ Cambios en Base de Datos

### Script SQL: `ALTER_TABLE_tw_inventario_ubicacion_detalle.sql`

```sql
ALTER TABLE tw_inventario_ubicacion_det
ADD COLUMN b_ajustado BOOLEAN DEFAULT FALSE,
ADD COLUMN s_motivo_ajuste VARCHAR(500),
ADD COLUMN d_fecha_ajuste DATETIME,
ADD COLUMN n_id_usuario_ajuste INT,
ADD CONSTRAINT fk_inventario_detalle_usuario_ajuste 
    FOREIGN KEY (n_id_usuario_ajuste) REFERENCES tc_usuarios(n_id);
```

**Nuevos campos:**
- `b_ajustado`: Flag que indica si el producto ya fue ajustado
- `s_motivo_ajuste`: Motivo del ajuste individual
- `d_fecha_ajuste`: Timestamp del ajuste
- `n_id_usuario_ajuste`: Usuario administrador que realizó el ajuste (INT para compatibilidad con tc_usuarios)

## 💻 Cambios en Frontend

### 1. **DTO Actualizado** (`InventarioUbicacionDto.ts`)
```typescript
export class InventarioUbicacionDetalleDto {
    // ...campos existentes
    
    // Campos de ajuste individual (nuevos)
    bAjustado?: boolean;
    sMotivoAjuste?: string;
    dFechaAjuste?: Date;
    nIdUsuarioAjuste?: number;
    sNombreUsuarioAjuste?: string;
}
```

### 2. **Servicio Actualizado** (`inventario-ubicacion.service.ts`)
```typescript
ajustarProductoInventario(
    inventarioId: number,
    productoId: number,
    motivoAjuste: string
): Observable<InventarioUbicacionDetalleDto>
```

### 3. **Locator Actualizado** (`locator.ts`)
```typescript
static ajustarProductoInventario = '/inventarios-ubicacion/'; 
// + inventarioId + '/detalle/' + productoId + '/ajustar'
```

### 4. **Componente Actualizado** (`autorizacion-inventario.component.ts`)
- **Nuevas variables:**
  - `mostrarDialogoAjustar`
  - `productoAjustar`
  - `motivoAjuste`

- **Nuevos métodos:**
  - `abrirDialogoAjustar(producto)`
  - `confirmarAjusteProducto()`
  
- **Nuevos getters:**
  - `todosProductosAjustados`: Valida si todos están ajustados
  - `productosPendientesAjuste`: Cuenta pendientes

### 5. **UI Actualizada** (`autorizacion-inventario.component.html`)
- **Tabla de productos:**
  - Nueva columna "Acciones"
  - Botón "Ajustar" (solo si no está ajustado)
  - Badge "Ajustado" con fecha (si ya fue ajustado)
  
- **Footer del diálogo:**
  - Mensaje de advertencia si hay productos pendientes
  - Botón "Autorizar" deshabilitado hasta que todos estén ajustados
  - Tooltip explicativo
  
- **Nuevo diálogo de ajuste:**
  - Muestra información del producto
  - Muestra diferencia visual (Sistema vs Contado)
  - Campo de motivo obligatorio
  - Mensaje informativo sobre actualización de tw_productobodega

## 🔧 Endpoint Backend Requerido

### `POST /inventarios-ubicacion/{inventarioId}/detalle/{productoId}/ajustar`

**Request Body:**
```json
{
  "sMotivoAjuste": "Diferencia validada por conteo físico"
}
```

**Lógica del endpoint:**
1. Validar que el inventario esté en estado `EN_REVISION`
2. Validar que el usuario tenga rol `ROLE_ADMIN`
3. Obtener el detalle del producto
4. Actualizar `tw_productobodega`:
   ```sql
   UPDATE tw_productobodega 
   SET n_cantidad = {cantidadContada}
   WHERE n_id_producto = {productoId} 
     AND n_id_bodega = {bodegaId}
     AND n_id_anaquel = {anaquelId}
     AND n_id_nivel = {nivelId}
   ```
5. Marcar el detalle como ajustado:
   ```sql
   UPDATE tw_inventario_ubicacion_det
   SET b_ajustado = TRUE,
       s_motivo_ajuste = {motivoAjuste},
       d_fecha_ajuste = NOW(),
       n_id_usuario_ajuste = {usuarioId}
   WHERE n_id_inventario = {inventarioId}
     AND n_id_producto = {productoId}
   ```
6. Retornar el detalle actualizado

**Response:**
```json
{
  "nId": 123,
  "nIdInventario": 45,
  "nIdProducto": 789,
  "sNoParte": "ABC-001",
  "sNombreProducto": "Producto X",
  "sMarca": "Marca Y",
  "nCantidadTeoricaRef": 100,
  "nCantidadContada": 95,
  "nDiferencia": -5,
  "bAjustado": true,
  "sMotivoAjuste": "Diferencia validada por conteo físico",
  "dFechaAjuste": "2026-02-07T13:45:00",
  "nIdUsuarioAjuste": 1,
  "sNombreUsuarioAjuste": "Admin User"
}
```

## ✅ Validaciones Implementadas

1. **Frontend:**
   - Motivo de ajuste es obligatorio
   - Botón "Autorizar" deshabilitado hasta que todos estén ajustados
   - Mensaje de advertencia visible con contador de pendientes
   
2. **Backend (Recomendado):**
   - Solo usuarios ROLE_ADMIN pueden ajustar
   - Solo inventarios en estado EN_REVISION pueden ajustarse
   - No permitir ajuste de productos sin diferencias
   - Validar que el producto pertenezca al inventario
   - Validar que no se pueda autorizar si hay productos sin ajustar

## 📊 Beneficios

1. **Trazabilidad completa**: Cada ajuste queda registrado con motivo, fecha y usuario
2. **Control granular**: Ajustes individuales en lugar de masivos
3. **Seguridad**: No se puede autorizar hasta que todos los ajustes estén completos
4. **Auditoría**: Historial completo de quién ajustó qué y por qué
5. **Flexibilidad**: El administrador puede revisar y ajustar producto por producto

## 🚀 Instrucciones de Implementación

1. **Ejecutar script SQL** en la base de datos:
   ```bash
   mysql -u usuario -p nombre_db < ALTER_TABLE_tw_inventario_ubicacion_detalle.sql
   ```

2. **Implementar endpoint** en el backend (Spring Boot)

3. **Compilar frontend** (ya implementado):
   ```bash
   npm start
   ```

4. **Probar el flujo completo:**
   - Cerrar un inventario con diferencias
   - Ir a "Autorización de Inventarios"
   - Verificar que el botón "Autorizar" esté deshabilitado
   - Ajustar cada producto con diferencia
   - Verificar que el botón se habilite al ajustar todos

## 📝 Notas Importantes

- Los productos SIN diferencias NO requieren ajuste
- Solo los productos con `nDiferencia !== 0` aparecen en la tabla y requieren ajuste
- El ajuste actualiza directamente `tw_productobodega` (tabla productiva)
- Una vez ajustado un producto, el botón "Ajustar" desaparece y muestra "Ajustado" con la fecha
- El administrador puede rechazar el inventario completo en cualquier momento (regresa a ABIERTO para correcciones)
