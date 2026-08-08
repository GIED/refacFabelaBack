# Inventario de Implementacion a Produccion (Creditos + Pagos + Complementos)

## 1) Script SQL unico de produccion

- Archivo: `scripts/RELEASE_PROD_CREDITOS_PAGOS_COMPLEMENTOS.sql`
- Objetivo: aplicar en una sola ejecucion todos los cambios de esquema requeridos para:
  - Complementos REP y clasificacion fiscal.
  - Pago global canonico (`tw_pago_cliente`, `tw_pago_aplicacion`).
  - Relacion legado-canonico (`tw_abonos.n_id_pago_cliente_canonico`).
  - Auditoria indexada (`tw_facturacion_pac_audit_detalle`).
  - Datos bancarios en cobro POS (`tr_venta_cobro`).

## 2) Cambios de base de datos incluidos

1. Alter de `tw_facturacion` para estados/uuid de complemento.
2. Creacion de `tw_facturacion_complemento_pago`.
3. Creacion de `tw_pago_cliente`.
4. Creacion de `tw_pago_aplicacion`.
5. FKs condicionales entre tablas de pagos/clientes/facturacion/usuarios.
6. Nueva columna `tw_abonos.n_id_pago_cliente_canonico` + indice + FK.
7. Creacion de `tw_facturacion_pac_audit_detalle`.
8. Ajuste de precision DECIMAL(10,2) en tablas de pagos.
9. Nueva columna `tw_pago_cliente.n_facturar_rep`.
10. Nuevos campos en `tr_venta_cobro` para identificacion bancaria:
   - `s_referencia`
   - `s_numero_autorizacion`
   - `s_ultimos4_tarjeta`
   - `n_id_cuenta_bancaria` + indice + FK
11. Ajustes de `tc_datos_factura` para compatibilidad con la version actual:
  - `s_token_api`
  - `n_predeterminado` + indice
  - `s_ruta_xml`, `s_ruta_pdf`, `s_ruta_raiz`

## 3) Scripts de pruebas que NO van en produccion

No ejecutar en produccion:
- `scripts/V011__mark_pac_audit_origen_y_preservar_historico.sql`
- `scripts/V012__drop_json_columns_tw_facturacion_pac_audit.sql`

Motivo: contienen logica de limpieza/purga para ambiente de pruebas.

## 4) Cambios de backend obligatorios (deploy de codigo)

Archivos detectados con cambios funcionales:
- `src/main/java/com/refacFabela/controller/VentasController.java`
- `src/main/java/com/refacFabela/model/TrVentaCobro.java`
- `src/main/java/com/refacFabela/service/impl/ProductosServiceImp.java`
- `src/main/java/com/refacFabela/repository/VentasFacturaRepository.java`

Resumen funcional:
- `guardarVentaCobro` regresa HTTP 400 para validaciones funcionales.
- `TrVentaCobro` incluye mapeo JPA de nuevos campos bancarios y cuenta bancaria.
- Validacion por clave SAT (`03`, `04`, `28`) para exigir `s_referencia`.
- Ajuste de consulta de ventas para factura (politica de elegibilidad fiscal).

## 5) Cambios de frontend obligatorios (deploy de codigo)

Archivos detectados con cambios funcionales:
- `src/app/caja/pages/cobrar/cobrar.component.html`
- `src/app/caja/pages/cobrar/cobrar.component.ts`
- `src/app/productos/model/TrVentaCobro.ts`
- `src/app/administracion/pages/creditos/creditos.component.html`
- `src/app/administracion/pages/creditos/creditos.component.ts`

Resumen funcional:
- Captura condicional de datos bancarios segun forma de pago.
- Modelo de cobro actualizado para enviar nuevos campos al backend.
- Mejoras de consulta de creditos/pagos/canonico y experiencia de navegacion.

## 6) Rutas en servidor y almacenamiento de comprobantes

El backend usa `ventas.internet.ruta-comprobantes` como ruta raiz para comprobantes.

Referencia de configuracion actual:
- `src/main/resources/application.properties`
  - `ventas.internet.ruta-comprobantes=/opt/webserver/backEnd/refacFabela/comprobantesInternet`

Controladores/servicios que usan esta ruta:
- `src/main/java/com/refacFabela/controller/VentasInternetController.java`
- `src/main/java/com/refacFabela/service/impl/DatosFacturaStorageResolver.java`
- `src/main/java/com/refacFabela/service/impl/TimbradoVentaService.java`
- `src/main/java/com/refacFabela/service/impl/GenerarReporteServiceImpl.java`

### Rutas reales que usa el codigo

1. Comprobantes de ventas internet (subida manual):
  - Ruta base: `ventas.internet.ruta-comprobantes`
  - Archivos: `${rutaBase}/{uuid}_{nombreOriginal}`

2. Facturas PDF/XML timbradas:
  - Si `tc_datos_factura.s_ruta_pdf` y/o `s_ruta_xml` tienen valor, se usan esas rutas.
  - Si no existen, usa `tc_datos_factura.s_ruta_raiz` + `/pdf` y `/xml`.
  - Si tampoco existe `s_ruta_raiz`, usa fallback `ventas.internet.ruta-comprobantes` + `/pdf` y `/xml`.
  - Archivos: `{idVenta}.pdf`, `{idVenta}.xml` y para parciales `{idVenta}_P{parcial}.pdf/xml`.

3. Complementos de pago (cache/mirror local para descargas):
  - Ruta: `${rutaRaiz}/complementos/pdf/{nIdComplemento}.pdf`
  - Ruta: `${rutaRaiz}/complementos/xml/{nIdComplemento}.xml`
  - Nota: el XML timbrado del complemento se persiste en BD (`tw_facturacion_complemento_pago.s_xml_timbrado`) y el espejo local se escribe en la primera descarga si no existia.

4. Acuses de cancelacion (cache/mirror local):
  - Ruta: `${rutaRaiz}/cancelaciones/pdf/{nIdVenta}.pdf`
  - Ruta: `${rutaRaiz}/cancelaciones/xml/{nIdVenta}.xml`

### Acciones requeridas en Linux produccion

1. Crear ruta raiz:
```bash
sudo mkdir -p /opt/webserver/backEnd/refacFabela/comprobantesInternet
```

2. Crear subdirectorios para salida por defecto (cuando no haya ruta personalizada en datos factura):
```bash
sudo mkdir -p /opt/webserver/backEnd/refacFabela/comprobantesInternet/pdf
sudo mkdir -p /opt/webserver/backEnd/refacFabela/comprobantesInternet/xml
sudo mkdir -p /opt/webserver/backEnd/refacFabela/comprobantesInternet/complementos/pdf
sudo mkdir -p /opt/webserver/backEnd/refacFabela/comprobantesInternet/complementos/xml
sudo mkdir -p /opt/webserver/backEnd/refacFabela/comprobantesInternet/cancelaciones/pdf
sudo mkdir -p /opt/webserver/backEnd/refacFabela/comprobantesInternet/cancelaciones/xml
```

3. Ajustar propietario/permisos para el usuario del servicio Java (ejemplo `ubuntu`):
```bash
sudo chown -R ubuntu:ubuntu /opt/webserver/backEnd/refacFabela/comprobantesInternet
sudo chmod -R 775 /opt/webserver/backEnd/refacFabela/comprobantesInternet
```

4. Si la ruta cambia por ambiente, definir variable:
```bash
export VENTAS_INTERNET_RUTA_COMPROBANTES=/ruta/real/en/produccion
```

5. Si usas rutas personalizadas por razon social en `tc_datos_factura`, crear estructura equivalente por cada raiz:
```bash
sudo mkdir -p <ruta_raiz>/pdf
sudo mkdir -p <ruta_raiz>/xml
sudo mkdir -p <ruta_raiz>/complementos/pdf
sudo mkdir -p <ruta_raiz>/complementos/xml
sudo mkdir -p <ruta_raiz>/cancelaciones/pdf
sudo mkdir -p <ruta_raiz>/cancelaciones/xml
sudo chown -R <usuario_servicio>:<grupo_servicio> <ruta_raiz>
sudo chmod -R 775 <ruta_raiz>
```

### Verificacion critica de rutas en `tc_datos_factura`

Si una razon social tiene `s_ruta_raiz`, `s_ruta_pdf` o `s_ruta_xml` con valor no vacio, el backend usara esos valores en vez del fallback global.

Ejecutar en produccion antes de liberar:

```sql
SELECT n_id, s_rfc_emisor, s_ruta_raiz, s_ruta_pdf, s_ruta_xml
FROM tc_datos_factura;
```

Consulta recomendada para obtener ruta raiz efectiva por razon social:

```sql
SELECT n_id,
       s_rfc_emisor,
       NULLIF(TRIM(s_ruta_raiz), '') AS ruta_raiz_configurada,
       NULLIF(TRIM(s_ruta_pdf), '')  AS ruta_pdf_configurada,
       NULLIF(TRIM(s_ruta_xml), '')  AS ruta_xml_configurada
FROM tc_datos_factura
ORDER BY n_id;
```

Si hay rutas de Windows (ejemplo `C:\` o `D:\`) en Linux, actualizar a ruta Linux valida o dejar NULL para usar fallback.

Deteccion rapida de rutas potencialmente invalidas en Linux:

```sql
SELECT n_id, s_rfc_emisor, s_ruta_raiz, s_ruta_pdf, s_ruta_xml
FROM tc_datos_factura
WHERE s_ruta_raiz REGEXP '^[A-Za-z]:\\\\'
  OR s_ruta_pdf  REGEXP '^[A-Za-z]:\\\\'
  OR s_ruta_xml  REGEXP '^[A-Za-z]:\\\\';
```

## 7) Endpoints relevantes que deben quedar expuestos

Facturacion/complementos:
- `GET /facturacion/complemento`
- `POST /facturacion/complemento/pago-cliente`
- `POST /facturacion/complementos/reintentar`
- `GET /facturacion/complementos`
- `GET /facturacion/getDocumentoComplemento`

Cobro POS:
- `POST /guardarVentaCobro`

Comprobantes internet:
- `POST /guardaComprobante`
- `GET /verComprobante/{nombreComprobante}`

## 8) Orden recomendado de despliegue

1. Respaldar BD y artefactos.
2. Ejecutar script SQL unico:
```bash
mysql -u <user> -p <database> < scripts/RELEASE_PROD_CREDITOS_PAGOS_COMPLEMENTOS.sql
```
3. Verificar creacion de ruta y permisos en servidor.
4. Desplegar backend (jar/servicio).
5. Desplegar frontend.
6. Pruebas smoke:
   - Cobro parcial y total en POS.
   - Pago global canonico y aplicacion a venta.
   - Generacion y descarga de complemento.
   - Consulta de creditos y trazabilidad abono/pago global.

## 8.1) Preflight recomendado (antes de ejecutar el script)

Validar que existen las tablas base sobre las que se aplican FKs y auditoria:

```sql
SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME IN (
    'tw_facturacion',
    'tw_facturacion_pac_audit',
    'tw_abonos',
    'tr_venta_cobro',
    'tc_datos_factura',
    'tc_clientes',
    'tc_formapago',
    'tc_usuarios',
    'tw_caja',
    'tw_ventas',
    'tc_cuentas_bancarias'
  )
ORDER BY TABLE_NAME;
```

Adicionalmente, validar catalogo de cuentas bancarias activo (para el flujo POS con `n_id_cuenta_bancaria`):

```sql
SELECT COUNT(*) AS total_cuentas_bancarias
FROM tc_cuentas_bancarias;
```

## 9) Validaciones post-release (SQL rapido)

```sql
SHOW TABLES LIKE 'tw_pago_cliente';
SHOW TABLES LIKE 'tw_pago_aplicacion';
SHOW TABLES LIKE 'tw_facturacion_complemento_pago';
SHOW TABLES LIKE 'tw_facturacion_pac_audit_detalle';

SHOW COLUMNS FROM tr_venta_cobro LIKE 's_referencia';
SHOW COLUMNS FROM tr_venta_cobro LIKE 'n_id_cuenta_bancaria';
SHOW COLUMNS FROM tw_abonos LIKE 'n_id_pago_cliente_canonico';
SHOW COLUMNS FROM tw_pago_cliente LIKE 'n_facturar_rep';
SHOW COLUMNS FROM tc_datos_factura LIKE 's_token_api';
SHOW COLUMNS FROM tc_datos_factura LIKE 'n_predeterminado';
SHOW COLUMNS FROM tc_datos_factura LIKE 's_ruta_raiz';

SHOW INDEX FROM tc_datos_factura WHERE Key_name = 'idx_tc_datos_factura_predeterminado';

SELECT n_id, n_predeterminado
FROM tc_datos_factura
ORDER BY n_id;
```
