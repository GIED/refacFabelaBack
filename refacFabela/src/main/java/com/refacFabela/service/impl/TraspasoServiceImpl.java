package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.TraspasoExternoDTO;
import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwAjustesInventario;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.ProductosRepository;
import com.refacFabela.repository.TwAjusteInventarioRepository;
import com.refacFabela.service.TraspasoService;
import com.refacFabela.utils.envioMail;

@Service
public class TraspasoServiceImpl implements TraspasoService {
	
	@Autowired
	private ProductoBodegaRepository productoBodegaRepository;
	@Autowired
	private TwAjusteInventarioRepository twAjusteInventarioRepository;
	@Autowired
	private ProductosRepository productosRepository;
	@Autowired
	private envioMail envioMail;
	
	
	/**
	 * Movimiento interno: cambio de ubicación (anaquel/nivel) dentro de la MISMA bodega.
	 * Lee la cantidad fresca de la BD para no sobreescribir con datos viejos del frontend.
	 * Solo guarda si realmente cambió la ubicación (evita trigger fantasma).
	 */
	@Override
	@Transactional
	public TwProductobodega guardar(TwProductobodega productoBodega) {
		
		TwProductobodega pb = productoBodegaRepository.obtenerStockBodegaForUpdate(
				productoBodega.getnIdProducto(), productoBodega.getnIdBodega());
		
		if (pb == null) {
			throw new RuntimeException("No se encontró el registro de bodega para el producto " 
					+ productoBodega.getnIdProducto() + " en bodega " + productoBodega.getnIdBodega());
		}

		// Solo actualizar ubicación, NUNCA la cantidad (eso lo hace guardarExterno)
		boolean cambio = false;
		
		if (productoBodega.getnIdAnaquel() != null && !productoBodega.getnIdAnaquel().equals(pb.getnIdAnaquel())) {
			pb.setnIdAnaquel(productoBodega.getnIdAnaquel());
			cambio = true;
		}
		if (productoBodega.getnIdNivel() != null && !productoBodega.getnIdNivel().equals(pb.getnIdNivel())) {
			pb.setnIdNivel(productoBodega.getnIdNivel());
			cambio = true;
		}
		
		if (cambio) {
			return this.productoBodegaRepository.save(pb);
		}
		
		// Sin cambios reales → no hacemos save → no se dispara el trigger
		return pb;
	}
	
	/**
	 * Movimiento interno 2: actualiza cantidad directamente (usado por ajuste de inventario).
	 * Lee la cantidad fresca de la BD con bloqueo pesimista.
	 */
	@Override
	@Transactional
	public TwProductobodega guardar2(TwProductobodega productoBodega) {
		
		TwProductobodega pb = productoBodegaRepository.obtenerStockBodegaForUpdate(
				productoBodega.getnIdProducto(), productoBodega.getnIdBodega());
		
		if (pb == null) {
			throw new RuntimeException("No se encontró el registro de bodega para el producto " 
					+ productoBodega.getnIdProducto() + " en bodega " + productoBodega.getnIdBodega());
		}
		
		// Actualizar cantidad con el valor que envía el frontend (ajuste directo)
		pb.setnCantidad(productoBodega.getnCantidad());
		
		// Actualizar ubicación si se envía
		if (productoBodega.getnIdAnaquel() != null) {
			pb.setnIdAnaquel(productoBodega.getnIdAnaquel());
		}
		if (productoBodega.getnIdNivel() != null) {
			pb.setnIdNivel(productoBodega.getnIdNivel());
		}
		
		return this.productoBodegaRepository.save(pb);
	}


	/**
	 * Movimiento externo: traspaso de mercancía ENTRE bodegas.
	 * 
	 * CORRECCIÓN CRÍTICA: 
	 * - Antes el frontend calculaba las cantidades y enviaba la lista completa.
	 *   Esto causaba race conditions cuando dos usuarios operaban simultáneamente.
	 * - Ahora el frontend solo envía: productoId, bodegaOrigen, bodegaDestino, cantidad.
	 * - El backend lee con SELECT FOR UPDATE, hace la aritmética y guarda.
	 * - Solo se modifican las 2 bodegas involucradas (no TODAS).
	 */
	@Override
	@Transactional
	public List<TwProductobodega> guardarExterno(TraspasoExternoDTO dto) {
		
		System.out.println("Traspaso externo: " + dto);
		
		// Validaciones básicas
		if (dto.getnCantidad() == null || dto.getnCantidad() <= 0) {
			throw new RuntimeException("La cantidad a traspasar debe ser mayor a 0");
		}
		if (dto.getnIdBodegaOrigen().equals(dto.getnIdBodegaDestino())) {
			throw new RuntimeException("La bodega origen y destino no pueden ser la misma");
		}
		
		// Leer con bloqueo pesimista (SELECT FOR UPDATE) — previene race conditions
		TwProductobodega bodegaOrigen = productoBodegaRepository.obtenerStockBodegaForUpdate(
				dto.getnIdProducto(), dto.getnIdBodegaOrigen());
		TwProductobodega bodegaDestino = productoBodegaRepository.obtenerStockBodegaForUpdate(
				dto.getnIdProducto(), dto.getnIdBodegaDestino());
		
		if (bodegaOrigen == null) {
			throw new RuntimeException("No se encontró registro de bodega origen (producto=" 
					+ dto.getnIdProducto() + ", bodega=" + dto.getnIdBodegaOrigen() + ")");
		}
		if (bodegaDestino == null) {
			throw new RuntimeException("No se encontró registro de bodega destino (producto=" 
					+ dto.getnIdProducto() + ", bodega=" + dto.getnIdBodegaDestino() + ")");
		}
		
		// Validar stock suficiente
		if (bodegaOrigen.getnCantidad() < dto.getnCantidad()) {
			throw new RuntimeException("Stock insuficiente en bodega origen. Disponible: " 
					+ bodegaOrigen.getnCantidad() + ", solicitado: " + dto.getnCantidad());
		}
		
		// Aritmética en el backend — NUNCA en el frontend
		bodegaOrigen.setnCantidad(bodegaOrigen.getnCantidad() - dto.getnCantidad());
		bodegaDestino.setnCantidad(bodegaDestino.getnCantidad() + dto.getnCantidad());
		
		// Guardar solo las 2 bodegas afectadas (no todas)
		productoBodegaRepository.save(bodegaOrigen);
		productoBodegaRepository.save(bodegaDestino);
		
		List<TwProductobodega> resultado = new ArrayList<>();
		resultado.add(bodegaOrigen);
		resultado.add(bodegaDestino);
		
		System.out.println("Traspaso completado: origen=" + bodegaOrigen.getnCantidad() 
				+ ", destino=" + bodegaDestino.getnCantidad());
		
		return resultado;
	}


	@Override
	@Transactional
	public TwAjustesInventario guardarAjusteInventario(TwAjustesInventario twAjustesInventario) {
		
		// Se asigna la fecha del movimiento
		Date date = new Date();
		twAjustesInventario.setsFecha(date);
		
		// Consulta del producto ajustado 
		TcProducto tcProducto= new TcProducto();
		tcProducto=productosRepository.findBynId(twAjustesInventario.getnIdProducto());
		

		// Envió de correo con el ajuste de inventario
		String mensaje="Se realizó un ajuste de inventario del producto: "+tcProducto.getsNoParte()+"-"+tcProducto.getsProducto()+" Anterior: "+twAjustesInventario.getnCantidadAnterior()+" Cantidad Actual: "+twAjustesInventario.getnCantidadActual()+" Cantidad Ajustada: "+twAjustesInventario.getnTotalAjustado()+" Motivo: "+twAjustesInventario.getsMotivo();
		envioMail.enviarCorreoEstandar("fabela_mauricio@hotmail.com", "Ajuste de inventario", mensaje);		
		
		// Se guarda el ajuste 		
		return twAjusteInventarioRepository.save(twAjustesInventario);
	}

}
