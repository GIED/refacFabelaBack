package com.refacFabela.service;

import java.util.Optional;

import com.refacFabela.model.VwProductoMetaCompra;

public interface VwProductoMetaCompraService {

	public Optional<VwProductoMetaCompra> buscarPorNoParte(String sNoParte);

}
