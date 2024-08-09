package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TwFacturasProveedor;
import com.refacFabela.model.VwFacturaProveedorBalance;
import com.refacFabela.repository.TwFacturasProveedorRepository;
import com.refacFabela.repository.VentasRepository;
import com.refacFabela.repository.VwFacturaProveedorBalanceRepository;
import com.refacFabela.service.FacturasProveedorService;
import com.refacFabela.tipoCambio.DataSerie;
import com.refacFabela.tipoCambio.Series;
import com.refacFabela.utils.utils;

@Service
public class TwFacturasProveedorServiceImpl implements FacturasProveedorService {

	@Autowired
	private TwFacturasProveedorRepository twFacturasProveedorRepository;
	
	@Autowired
	private VwFacturaProveedorBalanceRepository vwFacturaProveedorBalanceRepository;
	
	@Override
	public List<TwFacturasProveedor> obtenetTodas() {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.findBynEstatusFacturaProveedor(1L);
	}

	@Override
	public List<TwFacturasProveedor> obtenetFacturasProveedor(Long id) {
		// TODO Auto-generated method stub
		return twFacturasProveedorRepository.findBynIdProveedor(id);
	}

	@Override
	public List<VwFacturaProveedorBalance> obtenetBalanceProveedores() {		
	  		
		return vwFacturaProveedorBalanceRepository.findAll();
	}

	@Override
	public DataSerie obtenetTipoCambioBM() {
		
		DataSerie exchangeRate = Series.getExchangeRate();
		
		return exchangeRate;
	}

}
