package com.refacFabela.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refacFabela.dto.ActualizarConteoRequestDto;
import com.refacFabela.dto.AjustarProductoRequestDto;
import com.refacFabela.dto.AutorizarInventarioRequestDto;
import com.refacFabela.dto.IniciarInventarioRequestDto;
import com.refacFabela.dto.InventarioUbicacionDetalleDto;
import com.refacFabela.dto.InventarioUbicacionDto;
import com.refacFabela.dto.Mensaje;
import com.refacFabela.enums.RolNombre;
import com.refacFabela.model.TwInventarioUbicacionDetHist;
import com.refacFabela.model.UsuarioPrincipal;
import com.refacFabela.service.InventarioUbicacionService;

/**
 * Controlador REST para gestionar el inventario electrónico por ubicación.
 * Implementa validación de roles (ALMACEN vs ADMIN) según la funcionalidad.
 */
@RestController
@RequestMapping("/inventarios-ubicacion")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class InventarioUbicacionController {

    private static final Logger logger = LogManager.getLogger("errorLogger");

    @Autowired
    private InventarioUbicacionService inventarioService;

    /**
     * Obtener el usuario autenticado actual.
     */
    private UsuarioPrincipal getUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UsuarioPrincipal) authentication.getPrincipal();
    }

    /**
     * Verificar si el usuario tiene un rol específico.
     */
    private boolean tieneRol(RolNombre rol) {
        UsuarioPrincipal usuario = getUsuarioActual();
        return usuario.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals(rol.name()));
    }

    // ========== ENDPOINTS PARA ALMACÉN ==========

    /**
     * POST /inventarios-ubicacion
     * Iniciar nuevo inventario por ubicación (rol ALMACÉN).
     */
    @PostMapping
    public ResponseEntity<?> iniciarInventario(@RequestBody IniciarInventarioRequestDto request) {
        try {
            if (!tieneRol(RolNombre.ROLE_ALMACEN) && !tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("No tiene permisos para iniciar inventarios"));
            }

            UsuarioPrincipal usuario = getUsuarioActual();
            InventarioUbicacionDto inventario = inventarioService.iniciarInventario(request, usuario.getnId());

            return ResponseEntity.ok(inventario);

        } catch (Exception e) {
            logger.error("Error al iniciar inventario: " + e.getMessage(), e);
            
            // Si el error es por inventario existente, devolver el mensaje específico
            if (e.getMessage().contains("Ya existe un inventario")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new Mensaje(e.getMessage()));
            }
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al iniciar inventario: " + e.getMessage()));
        }
    }

    /**
     * POST /inventarios-ubicacion/{id}/sync
     * Sincronizar inventario con tw_productobodega (rol ALMACÉN).
     * Se ejecuta automáticamente al abrir/reanudar.
     */
    @PostMapping("/{id}/sync")
    public ResponseEntity<?> sincronizarInventario(@PathVariable Long id) {
        try {
            if (!tieneRol(RolNombre.ROLE_ALMACEN) && !tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("No tiene permisos para sincronizar inventarios"));
            }

            InventarioUbicacionDto inventario = inventarioService.sincronizarInventario(id);
            return ResponseEntity.ok(inventario);

        } catch (Exception e) {
            logger.error("Error al sincronizar inventario " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al sincronizar inventario: " + e.getMessage()));
        }
    }

    /**
     * PUT /inventarios-ubicacion/{id}/detalle/{productoId}
     * Actualizar conteo de un producto (rol ALMACÉN).
     */
    @PutMapping("/{id}/detalle/{productoId}")
    public ResponseEntity<?> actualizarConteo(
            @PathVariable Long id,
            @PathVariable Long productoId,
            @RequestBody ActualizarConteoRequestDto request) {
        try {
            if (!tieneRol(RolNombre.ROLE_ALMACEN) && !tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("No tiene permisos para capturar conteos"));
            }

            UsuarioPrincipal usuario = getUsuarioActual();
            InventarioUbicacionDetalleDto detalle = inventarioService.actualizarConteo(
                id, productoId, request, usuario.getnId()
            );

            return ResponseEntity.ok(detalle);

        } catch (Exception e) {
            logger.error("Error al actualizar conteo en inventario " + id + 
                        " producto " + productoId + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al actualizar conteo: " + e.getMessage()));
        }
    }

    /**
     * POST /inventarios-ubicacion/{id}/pausar
     * Pausar inventario (rol ALMACÉN).
     */
    @PostMapping("/{id}/pausar")
    public ResponseEntity<?> pausarInventario(@PathVariable Long id) {
        try {
            if (!tieneRol(RolNombre.ROLE_ALMACEN) && !tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("No tiene permisos para pausar inventarios"));
            }

            InventarioUbicacionDto inventario = inventarioService.pausarInventario(id);
            return ResponseEntity.ok(inventario);

        } catch (Exception e) {
            logger.error("Error al pausar inventario " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al pausar inventario: " + e.getMessage()));
        }
    }

    /**
     * POST /inventarios-ubicacion/{id}/reanudar
     * Reanudar inventario pausado (rol ALMACÉN).
     * Ejecuta sync automáticamente.
     */
    @PostMapping("/{id}/reanudar")
    public ResponseEntity<?> reanudarInventario(@PathVariable Long id) {
        try {
            if (!tieneRol(RolNombre.ROLE_ALMACEN) && !tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("No tiene permisos para reanudar inventarios"));
            }

            InventarioUbicacionDto inventario = inventarioService.reanudarInventario(id);
            return ResponseEntity.ok(inventario);

        } catch (Exception e) {
            logger.error("Error al reanudar inventario " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al reanudar inventario: " + e.getMessage()));
        }
    }

    /**
     * POST /inventarios-ubicacion/{id}/cerrar
     * Cerrar inventario para revisión (rol ALMACÉN).
     */
    @PostMapping("/{id}/cerrar")
    public ResponseEntity<?> cerrarInventario(@PathVariable Long id) {
        try {
            if (!tieneRol(RolNombre.ROLE_ALMACEN) && !tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("No tiene permisos para cerrar inventarios"));
            }

            UsuarioPrincipal usuario = getUsuarioActual();
            InventarioUbicacionDto inventario = inventarioService.cerrarInventario(id, usuario.getnId());

            return ResponseEntity.ok(inventario);

        } catch (Exception e) {
            logger.error("Error al cerrar inventario " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Mensaje("Error al cerrar inventario: " + e.getMessage()));
        }
    }

    // ========== ENDPOINTS PARA ADMIN ==========

    /**
     * POST /inventarios-ubicacion/{id}/detalle/{productoId}/ajustar
     * Ajustar producto individual con diferencia (rol ADMIN).
     * Actualiza tw_productobodega para ese producto específico.
     */
    @PostMapping("/{id}/detalle/{productoId}/ajustar")
    public ResponseEntity<?> ajustarProducto(
            @PathVariable Long id,
            @PathVariable Long productoId,
            @RequestBody AjustarProductoRequestDto request) {
        try {
            if (!tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("Solo el rol ADMIN puede ajustar productos"));
            }

            UsuarioPrincipal usuario = getUsuarioActual();
            InventarioUbicacionDetalleDto detalle = inventarioService.ajustarProducto(
                id, productoId, request.getsMotivoAjuste(), usuario.getnId()
            );

            return ResponseEntity.ok(detalle);

        } catch (Exception e) {
            logger.error("Error al ajustar producto " + productoId + 
                        " del inventario " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Mensaje("Error al ajustar producto: " + e.getMessage()));
        }
    }

    /**
     * POST /inventarios-ubicacion/{id}/autorizar
     * Autorizar inventario (rol ADMIN).
     */
    @PostMapping("/{id}/autorizar")
    public ResponseEntity<?> autorizarInventario(
            @PathVariable Long id,
            @RequestBody AutorizarInventarioRequestDto request) {
        try {
            if (!tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("Solo el rol ADMIN puede autorizar inventarios"));
            }

            UsuarioPrincipal usuario = getUsuarioActual();
            InventarioUbicacionDto inventario = inventarioService.autorizarInventario(
                id, request, usuario.getnId()
            );

            return ResponseEntity.ok(inventario);

        } catch (Exception e) {
            logger.error("Error al autorizar inventario " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Mensaje("Error al autorizar inventario: " + e.getMessage()));
        }
    }

    /**
     * POST /inventarios-ubicacion/{id}/aplicar
     * Aplicar inventario al stock operativo (rol ADMIN).
     * Actualiza tw_productobodega con las cantidades contadas.
     */
    @PostMapping("/{id}/aplicar")
    public ResponseEntity<?> aplicarInventario(@PathVariable Long id) {
        try {
            if (!tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("Solo el rol ADMIN puede aplicar inventarios"));
            }

            InventarioUbicacionDto inventario = inventarioService.aplicarInventario(id);
            return ResponseEntity.ok(inventario);

        } catch (Exception e) {
            logger.error("Error al aplicar inventario " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al aplicar inventario: " + e.getMessage()));
        }
    }

    // ========== ENDPOINTS DE CONSULTA (AMBOS ROLES) ==========

    /**
     * GET /inventarios-ubicacion/{id}
     * Obtener inventario completo con detalle.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerInventario(@PathVariable Long id) {
        try {
            InventarioUbicacionDto inventario = inventarioService.obtenerInventario(id);
            return ResponseEntity.ok(inventario);

        } catch (Exception e) {
            logger.error("Error al obtener inventario " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Mensaje("Inventario no encontrado"));
        }
    }

    /**
     * GET /inventarios-ubicacion/abierto-usuario
     * Obtener el inventario abierto/pausado del usuario actual.
     */
    @GetMapping("/abierto-usuario")
    public ResponseEntity<?> obtenerInventarioAbiertoUsuario() {
        try {
            UsuarioPrincipal usuario = getUsuarioActual();
            InventarioUbicacionDto inventario = inventarioService.obtenerInventarioAbiertoUsuario(usuario.getnId());

            if (inventario == null) {
                return ResponseEntity.ok(new Mensaje("No tiene inventarios abiertos"));
            }

            return ResponseEntity.ok(inventario);

        } catch (Exception e) {
            logger.error("Error al obtener inventario abierto del usuario: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al obtener inventario abierto"));
        }
    }

    /**
     * GET /inventarios-ubicacion
     * Listar todos los inventarios.
     */
    @GetMapping
    public ResponseEntity<?> listarInventarios() {
        try {
            List<InventarioUbicacionDto> inventarios = inventarioService.listarInventarios();
            return ResponseEntity.ok(inventarios);

        } catch (Exception e) {
            logger.error("Error al listar inventarios: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al listar inventarios"));
        }
    }

    /**
     * GET /inventarios-ubicacion/pendientes-autorizacion
     * Listar inventarios pendientes de autorización (estatus EN_REVISION).
     * Útil para la vista de ADMIN.
     */
    @GetMapping("/pendientes-autorizacion")
    public ResponseEntity<?> listarInventariosPendientesAutorizacion() {
        try {
            if (!tieneRol(RolNombre.ROLE_ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new Mensaje("Solo el rol ADMIN puede ver inventarios pendientes de autorización"));
            }

            List<InventarioUbicacionDto> inventarios = inventarioService.listarInventariosPendientesAutorizacion();
            return ResponseEntity.ok(inventarios);

        } catch (Exception e) {
            logger.error("Error al listar inventarios pendientes: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al listar inventarios pendientes"));
        }
    }

    /**
     * GET /inventarios-ubicacion/{id}/historial
     * Obtener historial de cambios del inventario.
     */
    @GetMapping("/{id}/historial")
    public ResponseEntity<?> obtenerHistorial(@PathVariable Long id) {
        try {
            List<TwInventarioUbicacionDetHist> historial = inventarioService.obtenerHistorial(id);
            return ResponseEntity.ok(historial);

        } catch (Exception e) {
            logger.error("Error al obtener historial del inventario " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al obtener historial"));
        }
    }

    /**
     * GET /inventarios-ubicacion/consulta
     * Consultar inventarios por ubicación con filtros opcionales.
     * Sin restricción de rol (controlado por menú en frontend).
     */
    @GetMapping("/consulta")
    public ResponseEntity<?> consultarInventariosPorUbicacion(
            @RequestParam(required = false) Long nIdBodega,
            @RequestParam(required = false) Long nIdAnaquel,
            @RequestParam(required = false) Long nIdNivel) {
        try {
            List<InventarioUbicacionDto> inventarios = inventarioService.consultarInventariosPorUbicacion(
                nIdBodega, nIdAnaquel, nIdNivel
            );
            return ResponseEntity.ok(inventarios);

        } catch (Exception e) {
            logger.error("Error al consultar inventarios por ubicación: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al consultar inventarios"));
        }
    }

    /**
     * GET /inventarios-ubicacion/por-producto/{productoId}
     * Consultar inventarios donde aparece un producto específico.
     * Sin restricción de rol (controlado por menú en frontend).
     */
    @GetMapping("/por-producto/{productoId}")
    public ResponseEntity<?> consultarInventariosPorProducto(@PathVariable Long productoId) {
        try {
            List<InventarioUbicacionDto> inventarios = inventarioService.consultarInventariosPorProducto(productoId);
            return ResponseEntity.ok(inventarios);

        } catch (Exception e) {
            logger.error("Error al consultar inventarios por producto " + productoId + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Mensaje("Error al consultar inventarios por producto"));
        }
    }
}
