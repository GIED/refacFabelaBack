package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.TvStockProductoDto;
import com.refacFabela.dto.VentaDto;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwVenta;

import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.ProductoBodegaRepository;

import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CotizacionRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.VentasService;
import com.refacFabela.utils.utils;

@Service
public class VentasServiceImpl implements VentasService {
	@Autowired
	private VentasRepository ventasRepository;

	@Autowired
	private VentasProductoRepository ventasProductoRepository;

	@Autowired
	private TvVentaDetalleRepository tvVentaDetalleRepository;

	@Autowired
	private ProductoBodegaRepository productoBodegaRepository;
	
	@Autowired
	private AbonoVentaIdRepository abonoVentaIdRepository;
	
	@Autowired
	private CotizacionRepository cotizacionRepository;
 

	@Override
	public List<TwVenta> consltaVentas() {

		return ventasRepository.findAll();
	}

	@Override
	public List<TvVentaDetalle> consultaVentaDetalleId(Long nIdCliente, Long nTipoPago) {

		return tvVentaDetalleRepository.consultaVentaDetalleId(nIdCliente, nTipoPago);
	}

	@Override
	public void guardarVenta(VentaDto ventaDto) {

		System.out.println(ventaDto);

		TwVenta twVenta = new TwVenta();

		twVenta.setnIdCliente(ventaDto.getIdCliente());
		twVenta.setnIdUsuario(1l);
		twVenta.setsFolioVenta(ventaDto.getsFolioVenta());
		twVenta.setnIdTipoVenta(ventaDto.getIdTipoVenta().longValue());
		twVenta.setnTipoPago(ventaDto.getTipoPago().longValue());
		twVenta.setdFechaInicioCredito(ventaDto.getFechaIniCredito());
		twVenta.setdFechaTerminoCredito(ventaDto.getFechaFinCredito());
		twVenta.setdFechaVenta(utils.fechaSistema);
		twVenta.setnIdCotizacion(ventaDto.getTwCotizacion().getnId());

		TwVenta ventaRegistrada = ventasRepository.save(twVenta);

		List<TwVentasProducto> listaProductos = new ArrayList<TwVentasProducto>();

		for (TvStockProductoDto producto : ventaDto.getListaValidada()) {

			TwVentasProducto twVentaProducto = new TwVentasProducto();

			twVentaProducto.setnIdVenta(ventaRegistrada.getnId());
			twVentaProducto.setnIdProducto(producto.getnIdProducto());
			twVentaProducto.setnCantidad(producto.getnCantidad());
			twVentaProducto.setnPrecioUnitario(producto.getTcProducto().getnPrecioSinIva());
			twVentaProducto.setnIvaUnitario(producto.getTcProducto().getnPrecioConIva() - producto.getTcProducto().getnPrecioSinIva());
			twVentaProducto.setnTotalUnitario(twVentaProducto.getnPrecioUnitario() + twVentaProducto.getnIvaUnitario());
			twVentaProducto.setnPrecioPartida(twVentaProducto.getnCantidad() * twVentaProducto.getnTotalUnitario());
			twVentaProducto.setnIvaPartida(twVentaProducto.getnPrecioPartida() * .16);
			twVentaProducto.setnTotalPartida(twVentaProducto.getnPrecioPartida() + twVentaProducto.getnIvaPartida());

			listaProductos.add(twVentaProducto);
		}

		this.ventasProductoRepository.saveAll(listaProductos);

		this.descuentaStock(listaProductos);
		
		TwCotizaciones twCotizaciones = ventaDto.getTwCotizacion();
		twCotizaciones.setnEstatus(2);		
		this.cotizacionRepository.save(twCotizaciones);

	}

	private void descuentaStock(List<TwVentasProducto> listaProductos) {

		for (TwVentasProducto twVentasProducto : listaProductos) {

			List<TwProductobodega> listaStock = productoBodegaRepository.findBynIdProducto(twVentasProducto.getnIdProducto());

			do {
				for (TwProductobodega twProBod : listaStock) {

					if (twProBod.getnIdBodega() == 1) { // inicia en bodega 1

						if (twProBod.getnCantidad() > 0) {// valida si hay stock en bodega 1

							if (twProBod.getnCantidad() >= twVentasProducto.getnCantidad()) {

								twProBod.setnCantidad(twProBod.getnCantidad() - twVentasProducto.getnCantidad());

								productoBodegaRepository.save(twProBod);// actualizamos stock
								twVentasProducto.setnCantidad(0);

							} else {

								twVentasProducto
										.setnCantidad(twVentasProducto.getnCantidad() - twProBod.getnCantidad());
								twProBod.setnCantidad(0);
								productoBodegaRepository.save(twProBod); // actualizamos stock

							}

						}

					} else if (twProBod.getnIdBodega() == 2) { // si no hay stock en bodega 1 entra a bodega 2

						if (twProBod.getnCantidad() > 0) {// valida si hay stock en bodega 1

							if (twProBod.getnCantidad() >= twVentasProducto.getnCantidad()) {

								twProBod.setnCantidad(twProBod.getnCantidad() - twVentasProducto.getnCantidad());

								productoBodegaRepository.save(twProBod);// actualizamos stock
								twVentasProducto.setnCantidad(0);

							} else {

								twVentasProducto
										.setnCantidad(twVentasProducto.getnCantidad() - twProBod.getnCantidad());
								twProBod.setnCantidad(0);
								productoBodegaRepository.save(twProBod); // actualizamos stock

							}

						}
					}

					else { // entra a la bodega 3

						if (twProBod.getnCantidad() >= twVentasProducto.getnCantidad()) {

							twProBod.setnCantidad(twProBod.getnCantidad() - twVentasProducto.getnCantidad());

							productoBodegaRepository.save(twProBod);// actualizamos stock
							twVentasProducto.setnCantidad(0);

						} else {

							twVentasProducto.setnCantidad(twVentasProducto.getnCantidad() - twProBod.getnCantidad());
							twProBod.setnCantidad(0);
							productoBodegaRepository.save(twProBod); // actualizamos stock

						}

					}

				}

			} while (twVentasProducto.getnCantidad() != 0);
		}

		}
		

	public List<TwAbono> consultaAbonoVentaId(Long nId) {
		
		return abonoVentaIdRepository.findBynIdVenta(nId);

	}

	@Override
	public List<TvVentaDetalle> consultaVentaDetalle() {
		// TODO Auto-generated method stub
		return tvVentaDetalleRepository.findAll();
	}

	

}
