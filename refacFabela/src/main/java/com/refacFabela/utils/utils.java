package com.refacFabela.utils;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwVentasProducto;
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
	
	public TcProducto calcularPrecio(TcProducto tcProducto, Double dolar, Double aumento, int cantidad ) {
		
		 double precioCal = 0;
	        double precioPeso = 0;
	        double precioPesofinalSinIva = 0;
	        double precioPesofinalIva = 0;
	        double precio_partida=0;
	        double iva_partida=0;
	        double precio_partida_total=0;
	        double iva=0.16;

	  

	        if (tcProducto.getsMoneda().equals("USD")) {
	            precioPeso = tcProducto.getnPrecio()  * dolar;
	        } else {
	            precioPeso =tcProducto.getnPrecio();
	        }
	        
	       
	        
	        precioPesofinalSinIva = (double) ((precioPeso * tcProducto.getTcGanancia().getnGanancia()) + precioPeso);           
	        if (aumento > 0) {
	            precioPesofinalSinIva = precioPesofinalSinIva + aumento;
	         }
	        
	        
	        
	        precioPesofinalIva = (double) (precioPesofinalSinIva) * iva;
	        precioCal = (double) (precioPesofinalSinIva + precioPesofinalIva);
	        precio_partida=precioPesofinalSinIva *  cantidad;          
	        iva_partida=precioPesofinalIva * cantidad;          
	        precio_partida_total=precioCal * cantidad;   
	                    
	         precioPesofinalSinIva=  Math.floor(precioPesofinalSinIva * 100) / 100;
	         precioPesofinalIva=  Math.floor(precioPesofinalIva * 100) / 100;
	         precio_partida=  Math.floor(precio_partida * 100) / 100;
	         iva_partida=  Math.floor(iva_partida * 100) / 100;
	         precio_partida_total=  Math.floor(precio_partida_total * 100) / 100;
	         precioCal=  Math.floor(precioCal * 100) / 100;
	         
	         tcProducto.setnPrecioPeso(precioCal);
	         tcProducto.setnPrecioSinIva( precioPesofinalSinIva);
	         tcProducto.setnPrecioConIva(precioCal);
	         tcProducto.setnPrecioIva(precioPesofinalIva);
	         
	         System.err.println(tcProducto);
		
	        return tcProducto;
		
	
	}
	
	public TwVentasProducto calcularPrecioGuardar(TcProducto tcProducto,  int cantidad ) {
		
			
		
		 	double precio_unitario_calculado = 0;
		 	double iva_unitario_calculado = 0;
	        double total_unitario_calculado = 0;
	        double precio_partida_calculado = 0;
	        double iva_partida_calculado=0;
	        double partida_total_calculado=0;
	        
	        double iva=0.16;
	        
	        
	        
	        TwVentasProducto twVentaProducto = new TwVentasProducto();

	        precio_unitario_calculado = (double) (tcProducto.getnPrecioSinIva());
	        iva_unitario_calculado = (double) (precio_unitario_calculado) * iva;
	        total_unitario_calculado=precio_unitario_calculado + iva_unitario_calculado;
	        precio_partida_calculado= precio_unitario_calculado *  cantidad;          
	        iva_partida_calculado=(precio_unitario_calculado * cantidad) * iva;          
	        partida_total_calculado=precio_partida_calculado + iva_partida_calculado;   
	                    
	         
	         
	         
	         	twVentaProducto.setnPrecioUnitario(precio_unitario_calculado);
				twVentaProducto.setnIvaUnitario(iva_unitario_calculado);
				twVentaProducto.setnTotalUnitario(total_unitario_calculado);
				twVentaProducto.setnPrecioPartida(precio_partida_calculado);
				twVentaProducto.setnIvaPartida(iva_partida_calculado);
				twVentaProducto.setnTotalPartida(partida_total_calculado);
				
				
				
		
		
		
	        return twVentaProducto;
		
	
	}

}
