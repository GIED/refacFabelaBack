package com.refacFabela.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.refacFabela.dto.DatoFacturaDto;
import com.refacFabela.dto.fechaDto;
import com.refacFabela.model.TcAnaquel;
import com.refacFabela.model.TcBodega;
import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcCategoria;
import com.refacFabela.model.TcCategoriaGeneral;
import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TcCp;
import com.refacFabela.model.TcCuentaBancaria;
import com.refacFabela.model.TcDatosFactura;
import com.refacFabela.model.TcEstatusFacturaProveedor;
import com.refacFabela.model.TcEstatusVenta;
import com.refacFabela.model.TcFormapago;
import com.refacFabela.model.TcGanancia;
import com.refacFabela.model.TcGasto;
import com.refacFabela.model.TcMarca;
import com.refacFabela.model.TcMoneda;
import com.refacFabela.model.TcNivel;
import com.refacFabela.model.TcRegimenFiscal;
import com.refacFabela.model.TcTipoVenta;
import com.refacFabela.model.TcUsocfdi;
import com.refacFabela.model.TwCaja;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.CatalagoFormaPagoRepository;
import com.refacFabela.repository.CatalagoTipoVentaRepository;
import com.refacFabela.repository.CatalagoUsoCfciRepository;
import com.refacFabela.repository.CatalogoAnaquelRepository;
import com.refacFabela.repository.CatalogoBodegasRepository;
import com.refacFabela.repository.CatalogoCategoriaRepository;
import com.refacFabela.repository.CatalogoClaveSatRepository;
import com.refacFabela.repository.CatalogoEstatusVentaRepository;
import com.refacFabela.repository.CatalogoGananciaRepository;
import com.refacFabela.repository.CatalogoNivelesRepository;
import com.refacFabela.repository.CatalogosRepository;
import com.refacFabela.repository.CategoriaGeneralRepository;
import com.refacFabela.repository.TcCpRepository;
import com.refacFabela.repository.TcCuentasBancariasRepository;
import com.refacFabela.repository.TcDatosFacturaRepository;
import com.refacFabela.repository.TcEstatusFacturaProveedorRepository;
import com.refacFabela.repository.TcGastoRepository;
import com.refacFabela.repository.TcMarcaRepository;
import com.refacFabela.repository.TcMonedaRepository;
import com.refacFabela.repository.TcRegimenFiscalRepository;
import com.refacFabela.service.CatalagosService;

@Service
public class CatalogosServiceImp implements CatalagosService {

	@Autowired
	private CatalogosRepository catalogosRepository;
	@Autowired
	private CatalogoClaveSatRepository catalogoClaveSatRepository;
	@Autowired
	private CategoriaGeneralRepository categoriaGeneralRepository;
	@Autowired
	private CatalogoCategoriaRepository catalogoCategoriaRepository;
	@Autowired
	private CatalogoGananciaRepository catalogoGananciaRepository;
	@Autowired
	private CatalogoAnaquelRepository catalogoAnaquelRepository;
	@Autowired
	private CatalogoNivelesRepository catalogoNivelesRepository;
	@Autowired
	private CatalogoBodegasRepository catalogoBodegasRepository;
	@Autowired
	private CatalagoTipoVentaRepository catalagoTipoVentaRepository;
	@Autowired
	private CatalagoFormaPagoRepository catalagoFormaPagoRepository;
	@Autowired
	private CatalagoUsoCfciRepository catalogoUsoCfdiRepository;
	@Autowired
	private CajaRepository cajaRepository;
	@Autowired
	private CatalogoEstatusVentaRepository catalogoEstatusVentaRepository;
	@Autowired
	private TcCpRepository tcCpRepository;
	@Autowired
	private TcRegimenFiscalRepository tcRegimenFiscalRepository;
	@Autowired
	private TcMarcaRepository tcMarcaRepository;
	
	@Autowired
	private TcGastoRepository tcGastoRepository;
	@Autowired
	private TcDatosFacturaRepository tcDatosFacturaRepository;
	@Autowired
	private TcMonedaRepository tcMonedaRepository;
	@Autowired
	private TcCuentasBancariasRepository tcCuentasBancariasRepository;
	
	@Autowired
	private TcEstatusFacturaProveedorRepository tcEstatusFacturaProveedorRepository;

	@Override
	public TcCatalogogeneral actualizarTipoCambio(TcCatalogogeneral ccCatalogogeneral) {

		return catalogosRepository.save(ccCatalogogeneral);
	}

	@Override
	public TcCatalogogeneral consultaTipoCambioId(TcCatalogogeneral ccCatalogogeneral) {

		return catalogosRepository.findBysClave(ccCatalogogeneral.getsClave());
	}

	@Override
	public List<TcClavesat> catalogoClaveSat() {
		

		return catalogoClaveSatRepository.obtenerClaveSat();
	}

	@Override
	public List<TcCategoriaGeneral> catalogoCategoriaGeneral() {

		return categoriaGeneralRepository.findAll(Sort.by(Sort.Direction.ASC, "sCategoriaGeneral"));
	}

	@Override
	public List<TcCategoria> catalogoCategoriaId(int id) {

		return catalogoCategoriaRepository.findBynIdCategoriaGeneral(id);
	}

	@Override
	public List<TcGanancia> catalogoGanancia() {

		return catalogoGananciaRepository.findAll();
	}
	
	@Override
	public TcGanancia catalogoGananciaId(Long nId) {
		
		return catalogoGananciaRepository.findById(nId).get();
	}

	@Override
	public List<TcAnaquel> catalogoAnaquel() {

		return catalogoAnaquelRepository.findAll();
	}

	@Override
	public List<TcNivel> catalogoNivel() {

		return catalogoNivelesRepository.findAll();
	}

	@Override
	public List<TcBodega> catalogoBodegas() {

		return catalogoBodegasRepository.findAll();
	}

	@Override
	public List<TcFormapago> catalogoFormaPago() {

		return catalagoFormaPagoRepository.findBynEstatus(1);
	}

	@Override
	public List<TcUsocfdi> catalagoUsoCfdi() {

		return catalogoUsoCfdiRepository.findBynEstatus(1);
	}

	@Override
	public List<TcTipoVenta> catalagoTipoVenta() {
		
		

		return catalagoTipoVentaRepository.findAll();
	}

	@Override
	public TwCaja cajaActiva() {		
		
		return cajaRepository.obtenerCajaVigente();
	}

	@Override
	public TcFormapago catalogoFormaPagoId(Long nId) {
		
		return catalagoFormaPagoRepository.getById(nId);
	}

	@Override
	public TcEstatusVenta catalagoEstatusVentaId(Long nId) {
		
		return catalogoEstatusVentaRepository.findBynId(nId);
	}

	@Override
	public TcCp catalagoCp(String cp) {
		
		return tcCpRepository.obtenerCp(cp);
	}

	@Override
	public List<TcRegimenFiscal> catalagoRegimenFiscal() {
		// TODO Auto-generated method stub
		return tcRegimenFiscalRepository.findAll(Sort.by(Sort.Direction.ASC, "sDescripcion"));
	}

	@Override
	public fechaDto fechaActual() {
		
		
		fechaDto fecha= new fechaDto();
		
		fecha.setFechaActual(new Date());
		
		
		return fecha ;
	}
	
	
	@Override
	public List<TcMarca> catalogoMarca() {		
			
		
		
		
		return tcMarcaRepository.findAll() ;
	}
	
	@Override
	public List<TcGasto> catalogoGasto() {	
			
		
		
		
		return tcGastoRepository.findAll() ;
	}

	@Override
	public List<DatoFacturaDto> catalogosDatoFactura() {
		
		
		List<DatoFacturaDto> ListaDatoFacturaDto=new ArrayList<DatoFacturaDto>();
		
		List<TcDatosFactura> listaDatosFacturas=tcDatosFacturaRepository.findAll();
		
		for (int i = 0; i < listaDatosFacturas.size(); i++) {
			DatoFacturaDto datoFacturaDto=new DatoFacturaDto();			
			datoFacturaDto.setnId(listaDatosFacturas.get(i).getnId());
			datoFacturaDto.setsRazonSocial(listaDatosFacturas.get(i).getsNombreEmisor());
			datoFacturaDto.setsRfc(listaDatosFacturas.get(i).getsRfcEmisor());
			
			ListaDatoFacturaDto.add(datoFacturaDto);
			
		}
		
		
		
		
		return ListaDatoFacturaDto;
	}

	@Override
	public List<TcMoneda> catalogoMoneda() {
		
		return tcMonedaRepository.findBynEstatus(1);
	}

	@Override
	public List<TcEstatusFacturaProveedor> catalogoEstatusFacturaProveedor() {
		return tcEstatusFacturaProveedorRepository.findBynEstatus(1);
	}

	@Override
	public List<TcCuentaBancaria> consultarCuentasBancariasRazon(Long nIdRazonSoacial) {
		// TODO Auto-generated method stub
		return tcCuentasBancariasRepository.consultaCuentas(nIdRazonSoacial);
	}

	

}
