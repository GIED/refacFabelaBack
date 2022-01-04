package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.VentaDto;
import com.refacFabela.model.TvVentaDetalle;
import com.refacFabela.model.TwAbono;
import com.refacFabela.model.TwCotizaciones;
import com.refacFabela.model.TwProductobodega;
import com.refacFabela.model.TwVenta;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.AbonoVentaIdRepository;
import com.refacFabela.repository.CotizacionRepository;
import com.refacFabela.repository.ProductoBodegaRepository;
import com.refacFabela.repository.TvVentaDetalleRepository;
import com.refacFabela.repository.VentasProductoRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.service.VentasService;
import com.refacFabela.utils.utils;


@Service
@Transactional
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
		twVenta.setnIdUsuario(ventaDto.getIdUsuario());
		twVenta.setsFolioVenta(ventaDto.getsFolioVenta());
		twVenta.setnIdTipoVenta(ventaDto.getIdTipoVenta().longValue());
		twVenta.setnTipoPago(ventaDto.getTipoPago().longValue());
		twVenta.setdFechaInicioCredito(ventaDto.getFechaIniCredito());
		twVenta.setdFechaTerminoCredito(ventaDto.getFechaFinCredito());
		twVenta.setdFechaVenta(utils.fechaSistema);
		twVenta.setnIdCotizacion(ventaDto.getTwCotizacion().getnId());

		TwVenta ventaRegistrada = ventasRepository.save(twVenta);

		List<TwVentasProducto> listaProductos = new ArrayList<TwVentasProducto>();

		for (int i= 0 ; i < ventaDto.getListaValidada().size(); i++) {

			TwVentasProducto twVentaProducto = new TwVentasProducto();

			twVentaProducto.setnIdVenta(ventaRegistrada.getnId());
			twVentaProducto.setnIdProducto(ventaDto.getListaValidada().get(i).getnIdProducto());
			twVentaProducto.setnCantidad(ventaDto.getListaValidada().get(i).getnCantidad());
			twVentaProducto.setnPrecioUnitario(ventaDto.getListaValidada().get(i).getTcProducto().getnPrecioSinIva());
			twVentaProducto.setnIvaUnitario(ventaDto.getListaValidada().get(i).getTcProducto().getnPrecioConIva() - ventaDto.getListaValidada().get(i).getTcProducto().getnPrecioSinIva());
			twVentaProducto.setnTotalUnitario(twVentaProducto.getnPrecioUnitario() + twVentaProducto.getnIvaUnitario());
			twVentaProducto.setnPrecioPartida(twVentaProducto.getnCantidad() * twVentaProducto.getnPrecioUnitario());
			twVentaProducto.setnIvaPartida(twVentaProducto.getnPrecioPartida() * .16);
			twVentaProducto.setnTotalPartida(twVentaProducto.getnPrecioPartida() + twVentaProducto.getnIvaPartida());
			twVentaProducto.setnIdUsuario(ventaDto.getIdUsuario());

			listaProductos.add(twVentaProducto);
		}

		this.ventasProductoRepository.saveAll(listaProductos);
		
		if (twVenta.getnIdTipoVenta()== 1L) {
			TwCotizaciones twCotizaciones = ventaDto.getTwCotizacion();
			twCotizaciones.setnEstatus(2);
			this.cotizacionRepository.save(twCotizaciones);			
			this.descuentaStock(listaProductos);
		}

	}

	private void descuentaStock(List<TwVentasProducto> listaProductos) {

		for (int i=0; i < listaProductos.size(); i++) {

			List<TwProductobodega> listaStock = new ArrayList<TwProductobodega>();
			
			listaStock = productoBodegaRepository
					.findBynIdProducto(listaProductos.get(i).getnIdProducto());
			
			int cantidad = listaProductos.get(i).getnCantidad();

			while (cantidad != 0) {
				
				for (TwProductobodega listaStockBodega : listaStock) {

					if (listaStockBodega.getnIdBodega() == 1) { // inicia en bodega 1

						if (listaStockBodega.getnCantidad() > 0) {// valida si hay stock en bodega 1

							if (listaStockBodega.getnCantidad() >= cantidad) {

								listaStockBodega.setnCantidad(listaStockBodega.getnCantidad() - cantidad);

								productoBodegaRepository.save(listaStockBodega);// actualizamos stock
								
								cantidad = 0;

							} else {

								cantidad = cantidad - listaStockBodega.getnCantidad();
								listaStockBodega.setnCantidad(0);
								productoBodegaRepository.save(listaStockBodega); // actualizamos stock

							}

						}

					} else if (listaStockBodega.getnIdBodega() == 2) { // si no hay stock en bodega 1 entra a bodega 2

						if (listaStockBodega.getnCantidad() > 0) {// valida si hay stock en bodega 2

							if (listaStockBodega.getnCantidad() >= cantidad) {

								listaStockBodega.setnCantidad(listaStockBodega.getnCantidad() - cantidad);

								productoBodegaRepository.save(listaStockBodega);// actualizamos stock
								cantidad = 0;

							} else {

								cantidad = cantidad - listaStockBodega.getnCantidad();
								listaStockBodega.setnCantidad(0);
								productoBodegaRepository.save(listaStockBodega); // actualizamos stock

							}

						}
					}

					else { // entra a la bodega 3

						if (listaStockBodega.getnCantidad() > 0) {// valida si hay stock en bodega 3

							if (listaStockBodega.getnCantidad() >= cantidad) {

								listaStockBodega.setnCantidad(listaStockBodega.getnCantidad() - cantidad);

								productoBodegaRepository.save(listaStockBodega);// actualizamos stock
								cantidad = 0;

							} else {

								cantidad = cantidad - listaStockBodega.getnCantidad();
								listaStockBodega.setnCantidad(0);
								productoBodegaRepository.save(listaStockBodega); // actualizamos stock

							}
						}

					}

				}

			} 
		}

	}

	public List<TwAbono> consultaAbonoVentaId(Long nId) {

		return abonoVentaIdRepository.findBynIdVenta(nId);

	}

	@Override
	public List<TvVentaDetalle> consultaVentaDetalle() {
	
		return tvVentaDetalleRepository.findAll();
	}


	@Override
	public List<TvVentaDetalle> consultaVentaDetalleEntrega() {
		
		return tvVentaDetalleRepository.consultaVentaDetalleEntrega();
	}

	
}
