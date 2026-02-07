package com.refacFabela.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.ActualizarConteoRequestDto;
import com.refacFabela.dto.AutorizarInventarioRequestDto;
import com.refacFabela.dto.IniciarInventarioRequestDto;
import com.refacFabela.dto.InventarioUbicacionDetalleDto;
import com.refacFabela.dto.InventarioUbicacionDto;
import com.refacFabela.model.TcAnaquel;
import com.refacFabela.model.TcBodega;
import com.refacFabela.model.TcNivel;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TcUsuario;
import com.refacFabela.model.TwInventarioUbicacion;
import com.refacFabela.model.TwInventarioUbicacionDet;
import com.refacFabela.model.TwInventarioUbicacionDetHist;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.repository.CatalogoAnaquelRepository;
import com.refacFabela.repository.CatalogoBodegasRepository;
import com.refacFabela.repository.CatalogoNivelesRepository;
import com.refacFabela.repository.TwInventarioUbicacionDetHistRepository;
import com.refacFabela.repository.TwInventarioUbicacionDetRepository;
import com.refacFabela.repository.TwInventarioUbicacionRepository;
import com.refacFabela.repository.TwProductoBodegaRepository;
import com.refacFabela.repository.UsuariosRepository;
import com.refacFabela.service.InventarioUbicacionService;

@Service
@Transactional
public class InventarioUbicacionServiceImpl implements InventarioUbicacionService {

    // Constantes de estatus de inventario
    private static final Integer ESTATUS_ABIERTO = 1;
    private static final Integer ESTATUS_PAUSADO = 2;
    private static final Integer ESTATUS_EN_REVISION = 3;
    private static final Integer ESTATUS_AUTORIZADO = 4;
    private static final Integer ESTATUS_APLICADO = 5;
    private static final Integer ESTATUS_CANCELADO = 6;

    // Constantes de estatus de línea
    private static final Integer ESTATUS_LINEA_PENDIENTE = 1;
    private static final Integer ESTATUS_LINEA_CONTADO = 2;
    private static final Integer ESTATUS_LINEA_RECONTAR = 3;

    @Autowired
    private TwInventarioUbicacionRepository inventarioRepository;

    @Autowired
    private TwInventarioUbicacionDetRepository detalleRepository;

    @Autowired
    private TwInventarioUbicacionDetHistRepository historialRepository;

    @Autowired
    private TwProductoBodegaRepository productoBodegaRepository;

    @Autowired
    private CatalogoBodegasRepository bodegasRepository;

    @Autowired
    private CatalogoAnaquelRepository anaquelRepository;

    @Autowired
    private CatalogoNivelesRepository nivelesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public InventarioUbicacionDto iniciarInventario(IniciarInventarioRequestDto request, Long usuarioId) throws Exception {
        // Validar que la ubicación exista
        validarUbicacionExiste(request.getnIdBodega(), request.getnIdAnaquel(), request.getnIdNivel());

        // Verificar si ya existe inventario abierto/pausado para esta ubicación
        Optional<TwInventarioUbicacion> inventarioUbicacionOpt = inventarioRepository.findInventarioAbiertoByUbicacion(
            request.getnIdBodega(), request.getnIdAnaquel(), request.getnIdNivel()
        );

        if (inventarioUbicacionOpt.isPresent()) {
            throw new Exception("Ya existe un inventario abierto/pausado para esta ubicación. ID: " + 
                              inventarioUbicacionOpt.get().getnId());
        }

        // Verificar si el usuario ya tiene un inventario abierto/pausado
        Optional<TwInventarioUbicacion> inventarioUsuarioOpt = inventarioRepository.findInventarioAbiertoByUsuario(usuarioId);
        if (inventarioUsuarioOpt.isPresent()) {
            throw new Exception("El usuario ya tiene un inventario abierto/pausado. ID: " + 
                              inventarioUsuarioOpt.get().getnId());
        }

        // Crear cabecera del inventario
        TwInventarioUbicacion inventario = new TwInventarioUbicacion();
        inventario.setnIdBodega(request.getnIdBodega());
        inventario.setnIdAnaquel(request.getnIdAnaquel());
        inventario.setnIdNivel(request.getnIdNivel());
        inventario.setnEstatus(ESTATUS_ABIERTO);
        inventario.setdInicio(LocalDateTime.now());
        inventario.setdUltimaActividad(LocalDateTime.now());
        inventario.setnIdUsuarioResponsable(usuarioId);
        inventario.setsObservaciones(request.getsObservaciones());

        inventario = inventarioRepository.save(inventario);

        // Crear detalle con snapshot inicial de tw_productobodega
        crearSnapshotInicial(inventario);

        return convertirADto(inventario);
    }

    /**
     * Crea el snapshot inicial del inventario desde tw_productobodega.
     */
    private void crearSnapshotInicial(TwInventarioUbicacion inventario) {
        // Obtener productos de la ubicación
        List<TwProductobodega> productos = productoBodegaRepository.obtenerProductosPorUbicacion(
            inventario.getnIdBodega(),
            inventario.getnIdAnaquel(),
            inventario.getnIdNivel()
        );

        LocalDateTime ahora = LocalDateTime.now();

        for (TwProductobodega pb : productos) {
            TwInventarioUbicacionDet detalle = new TwInventarioUbicacionDet();
            detalle.setnIdInventario(inventario.getnId());
            detalle.setnIdProducto(pb.getnIdProducto());
            detalle.setnCantidadTeoricaIni(pb.getnCantidad() != null ? pb.getnCantidad() : 0);
            detalle.setnCantidadTeoricaRef(pb.getnCantidad() != null ? pb.getnCantidad() : 0);
            detalle.setdRefActualizada(ahora);
            detalle.setnCantidadContada(null); // Pendiente
            detalle.setnEstatusLinea(ESTATUS_LINEA_PENDIENTE);

            detalleRepository.save(detalle);
        }
    }

    @Override
    public InventarioUbicacionDto sincronizarInventario(Long inventarioId) throws Exception {
        TwInventarioUbicacion inventario = obtenerInventarioPorId(inventarioId);

        // Validar que esté en estatus ABIERTO o PAUSADO
        if (!inventario.getnEstatus().equals(ESTATUS_ABIERTO) && 
            !inventario.getnEstatus().equals(ESTATUS_PAUSADO)) {
            throw new Exception("Solo se puede sincronizar inventarios en estatus ABIERTO o PAUSADO");
        }

        // Obtener productos actuales de tw_productobodega
        List<TwProductobodega> productosActuales = productoBodegaRepository.obtenerProductosPorUbicacion(
            inventario.getnIdBodega(),
            inventario.getnIdAnaquel(),
            inventario.getnIdNivel()
        );

        // Obtener detalle actual del inventario
        List<TwInventarioUbicacionDet> detalleActual = detalleRepository.findByInventarioId(inventarioId);

        LocalDateTime ahora = LocalDateTime.now();

        // 1. Insertar productos nuevos que no están en el detalle
        for (TwProductobodega pb : productosActuales) {
            boolean existe = detalleActual.stream()
                .anyMatch(d -> d.getnIdProducto().equals(pb.getnIdProducto()));

            if (!existe) {
                TwInventarioUbicacionDet nuevaLinea = new TwInventarioUbicacionDet();
                nuevaLinea.setnIdInventario(inventarioId);
                nuevaLinea.setnIdProducto(pb.getnIdProducto());
                nuevaLinea.setnCantidadTeoricaIni(pb.getnCantidad() != null ? pb.getnCantidad() : 0);
                nuevaLinea.setnCantidadTeoricaRef(pb.getnCantidad() != null ? pb.getnCantidad() : 0);
                nuevaLinea.setdRefActualizada(ahora);
                nuevaLinea.setnCantidadContada(null);
                nuevaLinea.setnEstatusLinea(ESTATUS_LINEA_PENDIENTE);

                detalleRepository.save(nuevaLinea);
            }
        }

        // 2. Actualizar n_cantidad_teorica_ref SOLO para líneas pendientes
        for (TwInventarioUbicacionDet linea : detalleActual) {
            if (linea.getnCantidadContada() == null) { // Solo pendientes
                Optional<TwProductobodega> pbOpt = productosActuales.stream()
                    .filter(pb -> pb.getnIdProducto().equals(linea.getnIdProducto()))
                    .findFirst();

                if (pbOpt.isPresent()) {
                    Integer cantidadActual = pbOpt.get().getnCantidad() != null ? pbOpt.get().getnCantidad() : 0;
                    if (!cantidadActual.equals(linea.getnCantidadTeoricaRef())) {
                        linea.setnCantidadTeoricaRef(cantidadActual);
                        linea.setdRefActualizada(ahora);
                        detalleRepository.save(linea);
                    }
                }
            }
        }

        // Actualizar última actividad
        inventario.setdUltimaActividad(ahora);
        inventarioRepository.save(inventario);

        return convertirADto(inventario);
    }

    @Override
    public InventarioUbicacionDto obtenerInventario(Long inventarioId) throws Exception {
        TwInventarioUbicacion inventario = obtenerInventarioPorId(inventarioId);
        return convertirADto(inventario);
    }

    @Override
    public InventarioUbicacionDto obtenerInventarioAbiertoUsuario(Long usuarioId) {
        Optional<TwInventarioUbicacion> inventarioOpt = inventarioRepository.findInventarioAbiertoByUsuario(usuarioId);
        return inventarioOpt.map(this::convertirADto).orElse(null);
    }

    @Override
    public List<InventarioUbicacionDto> listarInventarios() {
        List<TwInventarioUbicacion> inventarios = inventarioRepository.findAllOrderByInicio();
        return inventarios.stream()
            .map(this::convertirADto)
            .collect(Collectors.toList());
    }

    @Override
    public List<InventarioUbicacionDto> listarInventariosPendientesAutorizacion() {
        List<TwInventarioUbicacion> inventarios = inventarioRepository.findInventariosPendientesAutorizacion();
        return inventarios.stream()
            .map(this::convertirADto)
            .collect(Collectors.toList());
    }

    @Override
    public InventarioUbicacionDetalleDto actualizarConteo(Long inventarioId, Long productoId,
                                                         ActualizarConteoRequestDto request, Long usuarioId) throws Exception {
        TwInventarioUbicacion inventario = obtenerInventarioPorId(inventarioId);

        // Validar que esté en estatus ABIERTO o PAUSADO
        if (!inventario.getnEstatus().equals(ESTATUS_ABIERTO) && 
            !inventario.getnEstatus().equals(ESTATUS_PAUSADO)) {
            throw new Exception("Solo se puede capturar conteos en inventarios ABIERTOS o PAUSADOS");
        }

        // Buscar la línea del producto
        TwInventarioUbicacionDet linea = detalleRepository.findByInventarioAndProducto(inventarioId, productoId)
            .orElseThrow(() -> new Exception("Producto no encontrado en el inventario"));

        // Guardar valor anterior para auditoría
        Integer contadaAnterior = linea.getnCantidadContada();

        // Actualizar conteo
        linea.setnCantidadContada(request.getnCantidadContada());
        linea.setnEstatusLinea(ESTATUS_LINEA_CONTADO);
        linea.setdCaptura(LocalDateTime.now());
        linea.setnIdUsuarioCaptura(usuarioId);
        linea.setsObservacion(request.getsObservacion());

        detalleRepository.save(linea);

        // Registrar en historial
        TwInventarioUbicacionDetHist hist = new TwInventarioUbicacionDetHist();
        hist.setnIdInventario(inventarioId);
        hist.setnIdProducto(productoId);
        hist.setdEvento(LocalDateTime.now());
        hist.setnIdUsuario(usuarioId);
        hist.setnContadaAnterior(contadaAnterior);
        hist.setnContadaNueva(request.getnCantidadContada());
        hist.setsMotivo(request.getsMotivo() != null ? request.getsMotivo() : "Captura de conteo");

        historialRepository.save(hist);

        // Actualizar última actividad del inventario
        inventario.setdUltimaActividad(LocalDateTime.now());
        inventarioRepository.save(inventario);

        return convertirDetalleADto(linea);
    }

    @Override
    public InventarioUbicacionDto pausarInventario(Long inventarioId) throws Exception {
        TwInventarioUbicacion inventario = obtenerInventarioPorId(inventarioId);

        if (!inventario.getnEstatus().equals(ESTATUS_ABIERTO)) {
            throw new Exception("Solo se pueden pausar inventarios en estatus ABIERTO");
        }

        inventario.setnEstatus(ESTATUS_PAUSADO);
        inventario.setdUltimaActividad(LocalDateTime.now());
        inventarioRepository.save(inventario);

        return convertirADto(inventario);
    }

    @Override
    public InventarioUbicacionDto reanudarInventario(Long inventarioId) throws Exception {
        TwInventarioUbicacion inventario = obtenerInventarioPorId(inventarioId);

        if (!inventario.getnEstatus().equals(ESTATUS_PAUSADO)) {
            throw new Exception("Solo se pueden reanudar inventarios en estatus PAUSADO");
        }

        inventario.setnEstatus(ESTATUS_ABIERTO);
        inventario.setdUltimaActividad(LocalDateTime.now());
        inventarioRepository.save(inventario);

        // Ejecutar sincronización automática
        return sincronizarInventario(inventarioId);
    }

    @Override
    public InventarioUbicacionDto cerrarInventario(Long inventarioId, Long usuarioId) throws Exception {
        TwInventarioUbicacion inventario = obtenerInventarioPorId(inventarioId);

        // Validar que esté en estatus ABIERTO o PAUSADO
        if (!inventario.getnEstatus().equals(ESTATUS_ABIERTO) && 
            !inventario.getnEstatus().equals(ESTATUS_PAUSADO)) {
            throw new Exception("Solo se pueden cerrar inventarios en estatus ABIERTO o PAUSADO");
        }

        // Validar que no haya líneas pendientes (cantidad contada NULL)
        Long pendientes = detalleRepository.countPendientesByInventario(inventarioId);
        if (pendientes > 0) {
            throw new Exception("No se puede cerrar el inventario. Hay " + pendientes + " línea(s) pendiente(s) de contar");
        }

        inventario.setnEstatus(ESTATUS_EN_REVISION);
        inventario.setdCierre(LocalDateTime.now());
        inventario.setnIdUsuarioCierra(usuarioId);
        inventario.setdUltimaActividad(LocalDateTime.now());

        inventarioRepository.save(inventario);

        return convertirADto(inventario);
    }

    @Override
    public InventarioUbicacionDto autorizarInventario(Long inventarioId, AutorizarInventarioRequestDto request,
                                                     Long usuarioId) throws Exception {
        TwInventarioUbicacion inventario = obtenerInventarioPorId(inventarioId);

        if (!inventario.getnEstatus().equals(ESTATUS_EN_REVISION)) {
            throw new Exception("Solo se pueden autorizar inventarios en estatus EN_REVISION");
        }

        inventario.setnEstatus(ESTATUS_AUTORIZADO);
        inventario.setnIdUsuarioAutoriza(usuarioId);
        inventario.setdAutorizacion(LocalDateTime.now());
        inventario.setsMotivoAutorizacion(request.getsMotivoAutorizacion());
        inventario.setdUltimaActividad(LocalDateTime.now());

        inventarioRepository.save(inventario);

        return convertirADto(inventario);
    }

    @Override
    public InventarioUbicacionDto aplicarInventario(Long inventarioId) throws Exception {
        TwInventarioUbicacion inventario = obtenerInventarioPorId(inventarioId);

        if (!inventario.getnEstatus().equals(ESTATUS_AUTORIZADO)) {
            throw new Exception("Solo se pueden aplicar inventarios en estatus AUTORIZADO");
        }

        // Obtener detalle del inventario
        List<TwInventarioUbicacionDet> detalle = detalleRepository.findByInventarioId(inventarioId);

        // Actualizar tw_productobodega con las cantidades contadas
        for (TwInventarioUbicacionDet linea : detalle) {
            // Buscar el registro en tw_productobodega
            List<TwProductobodega> productos = productoBodegaRepository.obtenerProductosPorUbicacion(
                inventario.getnIdBodega(),
                inventario.getnIdAnaquel(),
                inventario.getnIdNivel()
            );

            Optional<TwProductobodega> pbOpt = productos.stream()
                .filter(pb -> pb.getnIdProducto().equals(linea.getnIdProducto()))
                .findFirst();

            if (pbOpt.isPresent()) {
                TwProductobodega pb = pbOpt.get();
                pb.setnCantidad(linea.getnCantidadContada());
                productoBodegaRepository.save(pb);
            } else {
                // Si no existe, crear el registro
                TwProductobodega nuevoPb = new TwProductobodega();
                nuevoPb.setnIdBodega(inventario.getnIdBodega());
                nuevoPb.setnIdAnaquel(inventario.getnIdAnaquel());
                nuevoPb.setnIdNivel(inventario.getnIdNivel());
                nuevoPb.setnIdProducto(linea.getnIdProducto());
                nuevoPb.setnCantidad(linea.getnCantidadContada());
                nuevoPb.setnEstatus(1L); // Activo
                productoBodegaRepository.save(nuevoPb);
            }
        }

        // Cambiar estatus a APLICADO
        inventario.setnEstatus(ESTATUS_APLICADO);
        inventario.setdUltimaActividad(LocalDateTime.now());
        inventarioRepository.save(inventario);

        return convertirADto(inventario);
    }

    @Override
    public List<TwInventarioUbicacionDetHist> obtenerHistorial(Long inventarioId) {
        return historialRepository.findByInventarioId(inventarioId);
    }

    // ========== Métodos auxiliares ==========

    private TwInventarioUbicacion obtenerInventarioPorId(Long inventarioId) throws Exception {
        return inventarioRepository.findById(inventarioId)
            .orElseThrow(() -> new Exception("Inventario no encontrado. ID: " + inventarioId));
    }

    private void validarUbicacionExiste(Long bodegaId, Long anaquelId, Long nivelId) throws Exception {
        if (!bodegasRepository.existsById(bodegaId)) {
            throw new Exception("Bodega no encontrada. ID: " + bodegaId);
        }
        if (!anaquelRepository.existsById(anaquelId)) {
            throw new Exception("Anaquel no encontrado. ID: " + anaquelId);
        }
        if (!nivelesRepository.existsById(nivelId)) {
            throw new Exception("Nivel no encontrado. ID: " + nivelId);
        }
    }

    private InventarioUbicacionDto convertirADto(TwInventarioUbicacion inventario) {
        InventarioUbicacionDto dto = new InventarioUbicacionDto();

        dto.setnId(inventario.getnId());
        dto.setnIdBodega(inventario.getnIdBodega());
        dto.setnIdAnaquel(inventario.getnIdAnaquel());
        dto.setnIdNivel(inventario.getnIdNivel());
        dto.setnEstatus(inventario.getnEstatus());
        dto.setsDescripcionEstatus(obtenerDescripcionEstatus(inventario.getnEstatus()));
        dto.setdInicio(inventario.getdInicio());
        dto.setdUltimaActividad(inventario.getdUltimaActividad());
        dto.setdCierre(inventario.getdCierre());
        dto.setnIdUsuarioResponsable(inventario.getnIdUsuarioResponsable());
        dto.setnIdUsuarioCierra(inventario.getnIdUsuarioCierra());
        dto.setnIdUsuarioAutoriza(inventario.getnIdUsuarioAutoriza());
        dto.setdAutorizacion(inventario.getdAutorizacion());
        dto.setsMotivoAutorizacion(inventario.getsMotivoAutorizacion());
        dto.setsObservaciones(inventario.getsObservaciones());

        // Nombres de catálogos
        if (inventario.getTcBodega() != null) {
            dto.setsBodega(inventario.getTcBodega().getsBodega());
        }
        if (inventario.getTcAnaquel() != null) {
            dto.setsAnaquel(inventario.getTcAnaquel().getsAnaquel());
        }
        if (inventario.getTcNivel() != null) {
            dto.setsNivel(inventario.getTcNivel().getsNivel());
        }

        // Nombres de usuarios
        if (inventario.getTcUsuarioResponsable() != null) {
            dto.setsNombreUsuarioResponsable(inventario.getTcUsuarioResponsable().getsNombreUsuario());
        }
        if (inventario.getTcUsuarioCierra() != null) {
            dto.setsNombreUsuarioCierra(inventario.getTcUsuarioCierra().getsNombreUsuario());
        }
        if (inventario.getTcUsuarioAutoriza() != null) {
            dto.setsNombreUsuarioAutoriza(inventario.getTcUsuarioAutoriza().getsNombreUsuario());
        }

        // Cargar detalle y estadísticas
        List<TwInventarioUbicacionDet> detalleList = detalleRepository.findByInventarioId(inventario.getnId());
        dto.setDetalle(detalleList.stream()
            .map(this::convertirDetalleADto)
            .collect(Collectors.toList()));

        // Calcular estadísticas
        dto.setTotalLineas(detalleList.size());
        dto.setLineasPendientes((int) detalleList.stream()
            .filter(d -> d.getnEstatusLinea().equals(ESTATUS_LINEA_PENDIENTE))
            .count());
        dto.setLineasContadas((int) detalleList.stream()
            .filter(d -> d.getnEstatusLinea().equals(ESTATUS_LINEA_CONTADO))
            .count());
        dto.setLineasRecontar((int) detalleList.stream()
            .filter(d -> d.getnEstatusLinea().equals(ESTATUS_LINEA_RECONTAR))
            .count());

        return dto;
    }

    private InventarioUbicacionDetalleDto convertirDetalleADto(TwInventarioUbicacionDet detalle) {
        InventarioUbicacionDetalleDto dto = new InventarioUbicacionDetalleDto();

        dto.setnId(detalle.getnId());
        dto.setnIdInventario(detalle.getnIdInventario());
        dto.setnIdProducto(detalle.getnIdProducto());
        dto.setnCantidadTeoricaIni(detalle.getnCantidadTeoricaIni());
        dto.setnCantidadTeoricaRef(detalle.getnCantidadTeoricaRef());
        dto.setdRefActualizada(detalle.getdRefActualizada());
        dto.setnCantidadContada(detalle.getnCantidadContada());
        dto.setnEstatusLinea(detalle.getnEstatusLinea());
        dto.setsDescripcionEstatusLinea(obtenerDescripcionEstatusLinea(detalle.getnEstatusLinea()));
        dto.setdCaptura(detalle.getdCaptura());
        dto.setnIdUsuarioCaptura(detalle.getnIdUsuarioCaptura());
        dto.setsObservacion(detalle.getsObservacion());

        // Información del producto
        if (detalle.getTcProducto() != null) {
            TcProducto producto = detalle.getTcProducto();
            dto.setsNoParte(producto.getsNoParte());
            dto.setsNombreProducto(producto.getsProducto());
            dto.setsMarca(producto.getsMarca());
        }

        // Usuario que capturó
        if (detalle.getTcUsuarioCaptura() != null) {
            dto.setsNombreUsuarioCaptura(detalle.getTcUsuarioCaptura().getsNombreUsuario());
        }

        // Calcular diferencia
        if (detalle.getnCantidadContada() != null && detalle.getnCantidadTeoricaRef() != null) {
            dto.setnDiferencia(detalle.getnCantidadContada() - detalle.getnCantidadTeoricaRef());
        }

        return dto;
    }

    private String obtenerDescripcionEstatus(Integer estatus) {
        switch (estatus) {
            case 1: return "ABIERTO";
            case 2: return "PAUSADO";
            case 3: return "EN_REVISION";
            case 4: return "AUTORIZADO";
            case 5: return "APLICADO";
            case 6: return "CANCELADO";
            default: return "DESCONOCIDO";
        }
    }

    private String obtenerDescripcionEstatusLinea(Integer estatus) {
        switch (estatus) {
            case 1: return "PENDIENTE";
            case 2: return "CONTADO";
            case 3: return "RECONTAR";
            default: return "DESCONOCIDO";
        }
    }
}
