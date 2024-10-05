package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.controller.BalanceAbonoProveedor;
import com.refacFabela.model.TwAbonoFacturaProveedor;
import com.refacFabela.model.TwFacturasProveedor;
import com.refacFabela.model.VwFacturaProveedorBalance;
import com.refacFabela.repository.TwFacturasProveedorRepository;
import com.refacFabela.repository.VwFacturaProveedorBalanceRepository;
import com.refacFabela.service.FacturasProveedorService;
import com.refacFabela.tipoCambio.DataSerie;
import com.refacFabela.tipoCambio.Series;

@Service
public class TwFacturasProveedorServiceImpl implements FacturasProveedorService {

	@Autowired
	private TwFacturasProveedorRepository twFacturasProveedorRepository;
	
	@Autowired
	private VwFacturaProveedorBalanceRepository vwFacturaProveedorBalanceRepository;
	
	@Autowired
	private BalanceFacturaProveedorRepository balanceFacturaProveedorRepository;
	@Autowired
	private TwAbonoFacturaProveedorRepository twAbonoFacturaProveedorRepository;
	
	@Override
	public List<TwFacturasProveedor> obtenetTodas() {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.findBynEstatusFacturaProveedor(1L);
	}

	public List<TwFacturasProveedor> obtenetFacturasProveedor(Long nIdProveedor, Long nIdMoneda) {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.findBynIdProveedor(nIdProveedor, nIdMoneda);
	}
	@Override
	public List<BalanceAbonoProveedor> obtenetFacturasProveedorBalance(Long nIdProveedor, Long nIdMoneda) {
		// TODO Auto-generated method stub
		return balanceFacturaProveedorRepository.findByBalance(nIdProveedor, nIdMoneda);
	}
	@Override
	public List<BalanceAbonoProveedor> obtenetFacturasProveedorBalanceHistoria(Long nIdProveedor, Long nIdMoneda) {
		// TODO Auto-generated method stub
		return balanceFacturaProveedorRepository.findByBalanceHistoria(nIdProveedor, nIdMoneda);
	}

	@Override
	public List<VwFacturaProveedorBalance> obtenetBalanceProveedores() {		
	  		
		return vwFacturaProveedorBalanceRepository.findAll();
	}

	@Override
	public DataSerie obtenetTipoCambioBM() {
		Series ser=new Series();
		DataSerie exchangeRate;
		try {
			exchangeRate = ser.getFirstDataSerie();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			exchangeRate=null;
		}
		
		return exchangeRate;
	}

	@Override
	public TwFacturasProveedor guardarFacturaProveedor(TwFacturasProveedor factura) {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.save(factura);
	}

	@Override
	public TwAbonoFacturaProveedor guardarAbonoFacturaProveedor(TwAbonoFacturaProveedor abono) {
		return twAbonoFacturaProveedorRepository.save(abono);
	}

	@Override
	public List<TwAbonoFacturaProveedor> obtenetAbonosFactura(Long nIdFactura) {
		// TODO Auto-generated method stub
		return twAbonoFacturaProveedorRepository.buscarAbono(nIdFactura);
	}

	@Override
	public BalanceAbonoProveedor obtenerBalanceFactura(Long nIdFactura) {
	
		return balanceFacturaProveedorRepository.findByBalanceFactura(nIdFactura);
	}

	@Override
	public TwFacturasProveedor obtenerFactura(Long nIdFactura) {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.findBynId(nIdFactura);
	}

	@Override
	public List<BalanceAbonoProveedor> obtenerFacturasSinPagar() {
		// TODO Auto-generated method stub
		return balanceFacturaProveedorRepository.findByFactutasSinPagar() ;
	}

}
