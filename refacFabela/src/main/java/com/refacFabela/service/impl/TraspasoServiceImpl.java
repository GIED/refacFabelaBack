package com.refacFabela.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	@Override
	@Transactional
	public TwProductobodega guardar(TwProductobodega productoBodega) {
		
		TwProductobodega pb =new TwProductobodega();
		
		pb=productoBodegaRepository.obtenerStockBodega(productoBodega.getnIdProducto(), productoBodega.getnIdBodega());
		productoBodega.setnCantidad(productoBodega.getnCantidad());
		
		return this.productoBodegaRepository.save(productoBodega);
	}


	@Override
	public List<TwProductobodega> guardarExterno(List<TwProductobodega> listProductoBodega) {
		
		
		
		return this.productoBodegaRepository.saveAll(listProductoBodega);
	}


	@Override
	public TwAjustesInventario guardarAjusteInventario(TwAjustesInventario twAjustesInventario) {
		
		// Se asigna la fecha del movimiento
		Date date = new Date();
		twAjustesInventario.setsFecha(date);
		
		// Consulta del producto ajustado 
		TcProducto tcProducto= new TcProducto();
		tcProducto=productosRepository.findBynId(twAjustesInventario.getnIdProducto());
		

		// Envió de correo con el ajuste de inventario
		String mensaje="Se realizó un ajuste de inventario del producto: "+tcProducto.getsNoParte()+"-"+tcProducto.getsProducto()+" Anterior: "+twAjustesInventario.getnCantidadAnterior()+" Cantidad Actual: "+twAjustesInventario.getnCantidadActual()+" Cantidad Ajustada: "+twAjustesInventario.getnTotalAjustado()+" Motivo: "+twAjustesInventario.getsMotivo();
		envioMail enviar=new envioMail();
		enviar.enviarCorreoEstandar("fabela_mauricio@hotmail.com", "Ajuste de inventario", mensaje);		
		
		// Se guarda el ajuste 		
		return twAjusteInventarioRepository.save(twAjustesInventario);
	}

}
