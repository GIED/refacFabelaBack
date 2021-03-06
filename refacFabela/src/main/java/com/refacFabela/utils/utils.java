package com.refacFabela.utils;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwCaja;
import com.refacFabela.repository.CajaRepository;

public  class utils {
	
	public static String filtroTipoCambio = "ValorCambio";
	public static String filtroIva = "ValorIva";
	public static Date fechaSistema = (new Date());
	public static LocalDateTime today = LocalDateTime.now();
	public static LocalDateTime tomorrow = today.plusDays(1);

	public String sumarRestarDiasFecha(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias); // numero de días a añadir, o restar en caso de días<0
		
		 SimpleDateFormat objSDF = new SimpleDateFormat("dd-MM-yyyy"); 
		return String.valueOf( objSDF.format(calendar.getTime())) ; // Devuelve el objeto Date con los nuevos días añadidos
	}
	
	public Long cajaActivaId(TwCaja caja) {
		
		Long noCaja=0L;
		noCaja=caja.getnId();			
		
		
		return noCaja;
	}
	
	public String formatoFecha(Date fecha) {
		 SimpleDateFormat objSDF = new SimpleDateFormat("dd-MM-yyyy"); 	
		 String fechaS= objSDF.format(fecha);
		
		return fechaS;
	}
	
	public Double truncarDecimales(Double value) {
		int decimalpoint=2;
		
		 value = value * Math.pow(10, decimalpoint);
	        value = Math.floor(value);
	        value = value / Math.pow(10, decimalpoint);
	  
	        System.out.println(value);
	  
	        return value;
		
	
	}
	
	public TcProducto calcularPrecio(TcProducto tcProducto) {
		
		
		
		tcProducto.setnPrecioSinIva(truncarDecimales (tcProducto.getnPrecioPeso())+(tcProducto.getnPrecioPeso()*tcProducto.getTcGanancia().getnGanancia()));
		tcProducto.setnPrecioConIva(truncarDecimales(tcProducto.getnPrecioSinIva()+(tcProducto.getnPrecioSinIva()*0.16)));
		
		
		
		
		
		
	        return tcProducto;
		
	
	}

}
