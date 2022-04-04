package com.refacFabela.model.factura;

import java.io.Serializable;

public class Impuesto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String base;
    public String impuesto;
    public String tipoFactor;
    public String tasaOCuota;
    public String importe;
    public String totalImpuestosTraslados;
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}
	public String getTipoFactor() {
		return tipoFactor;
	}
	public void setTipoFactor(String tipoFactor) {
		this.tipoFactor = tipoFactor;
	}
	public String getTasaOCuota() {
		return tasaOCuota;
	}
	public void setTasaOCuota(String tasaOCuota) {
		this.tasaOCuota = tasaOCuota;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getTotalImpuestosTraslados() {
		return totalImpuestosTraslados;
	}
	public void setTotalImpuestosTraslados(String totalImpuestosTraslados) {
		this.totalImpuestosTraslados = totalImpuestosTraslados;
	}
    
    

}
