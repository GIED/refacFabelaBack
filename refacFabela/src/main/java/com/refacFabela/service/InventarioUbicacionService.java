package com.refacFabela.service;

import java.util.List;

import com.refacFabela.dto.ActualizarConteoRequestDto;
import com.refacFabela.dto.AutorizarInventarioRequestDto;
import com.refacFabela.dto.IniciarInventarioRequestDto;
import com.refacFabela.dto.InventarioUbicacionDetalleDto;
import com.refacFabela.dto.InventarioUbicacionDto;
import com.refacFabela.model.TwInventarioUbicacion;
import com.refacFabela.model.TwInventarioUbicacionDetHist;

/**
 * Servicio para gestionar el inventario electrónico por ubicación.
 */
public interface InventarioUbicacionService {

    /**
     * Iniciar un nuevo inventario por ubicación (rol ALMACÉN).
     * Crea cabecera y detalle con snapshot inicial.
     * @param request Datos de ubicación y observaciones
     * @param usuarioId ID del usuario responsable
     * @return DTO del inventario creado
     * @throws Exception Si hay inventario abierto en la ubicación o usuario ya tiene uno abierto
     */
    InventarioUbicacionDto iniciarInventario(IniciarInventarioRequestDto request, Long usuarioId) throws Exception;

    /**
     * Sincronizar inventario con tw_productobodega (automático al abrir/reanudar).
     * Inserta productos nuevos y actualiza ref para pendientes.
     * @param inventarioId ID del inventario
     * @return DTO actualizado del inventario
     * @throws Exception Si el inventario no existe o no está en estatus adecuado
     */
    InventarioUbicacionDto sincronizarInventario(Long inventarioId) throws Exception;

    /**
     * Obtener inventario completo con detalle.
     * @param inventarioId ID del inventario
     * @return DTO completo del inventario
     * @throws Exception Si el inventario no existe
     */
    InventarioUbicacionDto obtenerInventario(Long inventarioId) throws Exception;

    /**
     * Obtener el inventario abierto/pausado del usuario.
     * @param usuarioId ID del usuario
     * @return DTO del inventario o null si no tiene ninguno abierto
     */
    InventarioUbicacionDto obtenerInventarioAbiertoUsuario(Long usuarioId);

    /**
     * Listar todos los inventarios (con filtros opcionales).
     * @return Lista de inventarios
     */
    List<InventarioUbicacionDto> listarInventarios();

    /**
     * Listar inventarios pendientes de autorización (estatus EN_REVISION).
     * @return Lista de inventarios pendientes
     */
    List<InventarioUbicacionDto> listarInventariosPendientesAutorizacion();

    /**
     * Actualizar conteo de un producto (rol ALMACÉN).
     * @param inventarioId ID del inventario
     * @param productoId ID del producto
     * @param request Datos del conteo
     * @param usuarioId ID del usuario que captura
     * @return Detalle actualizado
     * @throws Exception Si el inventario no está en estatus adecuado
     */
    InventarioUbicacionDetalleDto actualizarConteo(Long inventarioId, Long productoId, 
                                                   ActualizarConteoRequestDto request, Long usuarioId) throws Exception;

    /**
     * Pausar inventario (rol ALMACÉN).
     * @param inventarioId ID del inventario
     * @return DTO actualizado
     * @throws Exception Si el inventario no está ABIERTO
     */
    InventarioUbicacionDto pausarInventario(Long inventarioId) throws Exception;

    /**
     * Reanudar inventario pausado (rol ALMACÉN).
     * Al reanudar, ejecuta sync automáticamente.
     * @param inventarioId ID del inventario
     * @return DTO actualizado
     * @throws Exception Si el inventario no está PAUSADO
     */
    InventarioUbicacionDto reanudarInventario(Long inventarioId) throws Exception;

    /**
     * Cerrar inventario para revisión (rol ALMACÉN).
     * @param inventarioId ID del inventario
     * @param usuarioId ID del usuario que cierra
     * @return DTO actualizado
     * @throws Exception Si hay líneas pendientes o estatus incorrecto
     */
    InventarioUbicacionDto cerrarInventario(Long inventarioId, Long usuarioId) throws Exception;

    /**
     * Ajustar producto individual (rol ADMIN).
     * Actualiza tw_productobodega para un producto específico con diferencia.
     * @param inventarioId ID del inventario
     * @param productoId ID del producto a ajustar
     * @param motivoAjuste Motivo del ajuste
     * @param usuarioId ID del usuario (admin) que ajusta
     * @return Detalle actualizado con campos de ajuste poblados
     * @throws Exception Si el inventario no está EN_REVISION o el producto no tiene diferencia
     */
    InventarioUbicacionDetalleDto ajustarProducto(Long inventarioId, Long productoId, 
                                                  String motivoAjuste, Long usuarioId) throws Exception;

    /**
     * Autorizar inventario (rol ADMIN).
     * @param inventarioId ID del inventario
     * @param request Motivo de autorización
     * @param usuarioId ID del usuario que autoriza
     * @return DTO actualizado
     * @throws Exception Si el inventario no está EN_REVISION
     */
    InventarioUbicacionDto autorizarInventario(Long inventarioId, AutorizarInventarioRequestDto request, 
                                              Long usuarioId) throws Exception;

    /**
     * Aplicar inventario al stock operativo (rol ADMIN).
     * Actualiza tw_productobodega con conteos finales.
     * @param inventarioId ID del inventario
     * @return DTO actualizado
     * @throws Exception Si el inventario no está AUTORIZADO
     */
    InventarioUbicacionDto aplicarInventario(Long inventarioId) throws Exception;

    /**
     * Obtener historial de cambios de un inventario.
     * @param inventarioId ID del inventario
     * @return Lista de eventos históricos
     */
    List<TwInventarioUbicacionDetHist> obtenerHistorial(Long inventarioId);
}
