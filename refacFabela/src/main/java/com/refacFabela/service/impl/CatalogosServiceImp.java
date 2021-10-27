package com.refacFabela.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refacFabela.model.TcCatalogogeneral;
import com.refacFabela.model.TcCategoria;
import com.refacFabela.model.TcCategoriaGeneral;
import com.refacFabela.model.TcClavesat;
import com.refacFabela.model.TcGanancia;
import com.refacFabela.repository.CatalogoCategoriaRepository;
import com.refacFabela.repository.CatalogoClaveSatRepository;
import com.refacFabela.repository.CatalogoGananciaRepository;
import com.refacFabela.repository.CatalogosRepository;
import com.refacFabela.repository.CategoriaGeneralRepository;
import com.refacFabela.service.CatalagosService;

import net.bytebuddy.asm.Advice.Return;

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
	
	@Override
	public TcCatalogogeneral actualizarTipoCambio(TcCatalogogeneral ccCatalogogeneral) {
		
		return catalogosRepository.save(ccCatalogogeneral);
	}

	@Override
	public TcCatalogogeneral consultaTipoCambioId(TcCatalogogeneral ccCatalogogeneral) {
		
		return catalogosRepository.findBysClave(ccCatalogogeneral.getSClave());
	}

	@Override
	public List<TcClavesat> catalogoClaveSat() {
		
		return catalogoClaveSatRepository.findAll();
	}

	@Override
	public List<TcCategoriaGeneral> catalogoCategoriaGeneral() {
		
		return categoriaGeneralRepository.findAll() ;
	}

	@Override
	public List<TcCategoria> catalogoCategoriaId(int id) {
		
		return  catalogoCategoriaRepository.findBynIdCategoriaGeneral(id);
	}

	@Override
	public List<TcGanancia> catalogoGanancia() {
		
		return catalogoGananciaRepository.findAll();
	}
	

}
