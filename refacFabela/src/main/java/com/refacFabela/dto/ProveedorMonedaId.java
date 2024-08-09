package com.refacFabela.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProveedorMonedaId implements Serializable {

	   @Column(name = "n_id_proveedor")
	    private Long nIdProveedor;

	    @Column(name = "n_id_moneda")
	    private Long nIdMoneda;

	    public ProveedorMonedaId() {}

	    public ProveedorMonedaId(Long nIdProveedor, Long nIdMoneda) {
	        this.nIdProveedor = nIdProveedor;
	        this.nIdMoneda = nIdMoneda;
	    }

	    // Getters y setters

	    public Long getnIdProveedor() {
	        return nIdProveedor;
	    }

	    public void setnIdProveedor(Long nIdProveedor) {
	        this.nIdProveedor = nIdProveedor;
	    }

	    public Long getnIdMoneda() {
	        return nIdMoneda;
	    }

	    public void setnIdMoneda(Long nIdMoneda) {
	        this.nIdMoneda = nIdMoneda;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        ProveedorMonedaId that = (ProveedorMonedaId) o;
	        return Objects.equals(nIdProveedor, that.nIdProveedor) &&
	               Objects.equals(nIdMoneda, that.nIdMoneda);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(nIdProveedor, nIdMoneda);
	    }
	}