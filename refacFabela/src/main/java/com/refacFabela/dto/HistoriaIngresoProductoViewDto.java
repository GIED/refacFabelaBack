package com.refacFabela.dto;

import java.time.LocalDateTime;

import com.refacFabela.repository.HistoriaIngresoProductoView;

public class HistoriaIngresoProductoViewDto {

	
	    Long nIdProducto;

	    String sFolioFactura;

	    String sRazonSocial;

	    Integer nCantidad;     

	    LocalDateTime dFechaIngreso;

	    String sUbicacion;

	    String sNombreUsuario;

		public Long getnIdProducto() {
			return nIdProducto;
		}

		public void setnIdProducto(Long nIdProducto) {
			this.nIdProducto = nIdProducto;
		}

		public String getsFolioFactura() {
			return sFolioFactura;
		}

		public void setsFolioFactura(String sFolioFactura) {
			this.sFolioFactura = sFolioFactura;
		}

		public String getsRazonSocial() {
			return sRazonSocial;
		}

		public void setsRazonSocial(String sRazonSocial) {
			this.sRazonSocial = sRazonSocial;
		}

		public Integer getnCantidad() {
			return nCantidad;
		}

		public void setnCantidad(Integer nCantidad) {
			this.nCantidad = nCantidad;
		}

		public LocalDateTime getdFechaIngreso() {
			return dFechaIngreso;
		}

		public void setdFechaIngreso(LocalDateTime dFechaIngreso) {
			this.dFechaIngreso = dFechaIngreso;
		}

		public String getsUbicacion() {
			return sUbicacion;
		}

		public void setsUbicacion(String sUbicacion) {
			this.sUbicacion = sUbicacion;
		}

		public String getsNombreUsuario() {
			return sNombreUsuario;
		}

		public void setsNombreUsuario(String sNombreUsuario) {
			this.sNombreUsuario = sNombreUsuario;
		}

		@Override
		public String toString() {
			return "HistoriaIngresoProductoViewDto [nIdProducto=" + nIdProducto + ", sFolioFactura=" + sFolioFactura
					+ ", sRazonSocial=" + sRazonSocial + ", nCantidad=" + nCantidad + ", dFechaIngreso=" + dFechaIngreso
					+ ", sUbicacion=" + sUbicacion + ", sNombreUsuario=" + sNombreUsuario + "]";
		}   
	    
	    public HistoriaIngresoProductoViewDto() {
	    	
	    	
	    }
	    
	    public HistoriaIngresoProductoViewDto(HistoriaIngresoProductoView v) {
	        this.nIdProducto = v.getnIdProducto();
	        this.sFolioFactura = v.getsFolioFactura();
	        this.sRazonSocial = v.getsRazonSocial();
	        this.nCantidad = v.getnCantidad();
	        this.dFechaIngreso = v.getdFechaIngreso();
	        this.sUbicacion = v.getsUbicacion();
	        this.sNombreUsuario = v.getsNombreUsuario();
	    }
	    
}
