package com.refacFabela.enums;

import com.refacFabela.utils.factura.*;

public enum TipoDoc {
		
	
	PDF_FACTURA(ConstantesFactura.rutaPdf), 
	XML_FACTURA(ConstantesFactura.rutaxmlTimbrado),
	PDF_PROVEEDOR(ConstantesFactura.rutaPdfProveedor);
	

	
	private String path;
	   private TipoDoc(String path) {
	      this.path = path;
	   }

	   public String getPath() {
	      return path;
	   }
	


}

