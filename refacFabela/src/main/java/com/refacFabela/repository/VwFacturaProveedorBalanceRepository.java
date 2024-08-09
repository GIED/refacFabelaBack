package com.refacFabela.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refacFabela.dto.ProveedorMonedaId;
import com.refacFabela.model.VwFacturaProveedorBalance;

public interface VwFacturaProveedorBalanceRepository extends JpaRepository<VwFacturaProveedorBalance, ProveedorMonedaId> {

}
