package com.refacFabela.dto;

import org.springframework.web.multipart.MultipartFile;

public class SubirFacturaDto {
	
	public MultipartFile pdfFile;
	public MultipartFile xmlFile;
	public String Suuid;
	public Boolean estatus;
	public String mensaje;
	public Long nIdVenta;
	public MultipartFile getPdfFile() {
		return pdfFile;
	}
	public void setPdfFile(MultipartFile pdfFile) {
		this.pdfFile = pdfFile;
	}
	public MultipartFile getXmlFile() {
		return xmlFile;
	}
	public void setXmlFile(MultipartFile xmlFile) {
		this.xmlFile = xmlFile;
	}
	public String getSuuid() {
		return Suuid;
	}
	public void setSuuid(String suuid) {
		Suuid = suuid;
	}
	public Boolean getEstatus() {
		return estatus;
	}
	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Long getnIdVenta() {
		return nIdVenta;
	}
	public void setnIdVenta(Long nIdVenta) {
		this.nIdVenta = nIdVenta;
	}
	
	

}
