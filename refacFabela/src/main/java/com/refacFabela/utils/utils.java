package com.refacFabela.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
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
	public static Date fechaSistema = new Date();
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
		
		

		
		 
	  
	        return Math.floor(value * 100) / 100;
		
	
	}
	
	public TcProducto calcularPrecio(TcProducto tcProducto, Double dolar, Double aumento, int cantidad, boolean descuento ) {
				
	        BigDecimal precioPeso = BigDecimal.ZERO;
	        BigDecimal precioPesofinalSinIva = BigDecimal.ZERO;
	        BigDecimal precio_unitario_calculado = BigDecimal.ZERO;
	        BigDecimal iva_unitario_calculado = BigDecimal.ZERO;
	        BigDecimal total_unitario_calculado = BigDecimal.ZERO;   	        
	        BigDecimal iva=new BigDecimal("0.16");
	       
          
	        if (tcProducto.getsMoneda().equals("USD")) {
	            precioPeso = tcProducto.getnPrecio().multiply(BigDecimal.valueOf(dolar));
	        } else {
	            precioPeso =tcProducto.getnPrecio();
	            }      	
	        
	        if(descuento) {	
	        	if(tcProducto.getTcDescuento().getnId()>0) {
	        	tcProducto.setsProducto(tcProducto.getsProducto()+"-"+tcProducto.getTcDescuento().getnId()+"%");
	        	}
	        	precioPeso=precioPeso.subtract ((precioPeso.multiply(BigDecimal.valueOf( tcProducto.getTcDescuento().getnGanancia()))));
	        	
	        }
	        
	        
	        
	        precioPesofinalSinIva = precioPeso.add(precioPeso.multiply(BigDecimal.valueOf(tcProducto.getTcGanancia().getnGanancia())));
	        	        
	        if (aumento > 0) {
	            precioPesofinalSinIva = precioPesofinalSinIva.add(BigDecimal.valueOf(aumento)) ;
	         }	          	        
	        precio_unitario_calculado = DateTimeUtil.truncarDosDecimales(precioPesofinalSinIva) ;
	        iva_unitario_calculado =   DateTimeUtil.truncarDosDecimales(precio_unitario_calculado.multiply(iva)); 
	        total_unitario_calculado= precio_unitario_calculado.add(iva_unitario_calculado) ;
	        
	         tcProducto.setnPrecioPeso(total_unitario_calculado);
	         tcProducto.setnPrecioSinIva( precio_unitario_calculado);
	         tcProducto.setnPrecioConIva(total_unitario_calculado);
	         tcProducto.setnPrecioIva(iva_unitario_calculado);	         
	       
		     return tcProducto;
		
	
	}
	
	
	
	public TwVentasProducto calcularPrecioGuardar(TcProducto tcProducto,  int cantidad ) {
		
			
		
		BigDecimal precio_unitario_calculado = BigDecimal.ZERO;
		BigDecimal iva_unitario_calculado = BigDecimal.ZERO;
		BigDecimal total_unitario_calculado = BigDecimal.ZERO;
		BigDecimal precio_partida_calculado = BigDecimal.ZERO;
		BigDecimal iva_partida_calculado=BigDecimal.ZERO;
		BigDecimal partida_total_calculado=BigDecimal.ZERO;	        
		BigDecimal iva=new BigDecimal("0.16");
	        
	        
	        
	        TwVentasProducto twVentaProducto = new TwVentasProducto();
	        
	        

	        precio_unitario_calculado = DateTimeUtil.truncarDosDecimales(tcProducto.getnPrecioSinIva()) ;
	        iva_unitario_calculado = precio_unitario_calculado.multiply(iva) ; 
	        total_unitario_calculado=precio_unitario_calculado.add(iva_unitario_calculado) ;
	        precio_partida_calculado= precio_unitario_calculado.multiply(BigDecimal.valueOf(cantidad));         
	        iva_partida_calculado=iva_unitario_calculado.multiply( BigDecimal.valueOf(cantidad));          
	        partida_total_calculado=total_unitario_calculado.multiply(BigDecimal.valueOf(cantidad)) ;   
	                    
	         
	         
	         
	         	twVentaProducto.setnPrecioUnitario( DateTimeUtil.truncarDosDecimales(precio_unitario_calculado));
				twVentaProducto.setnIvaUnitario( DateTimeUtil.truncarDosDecimales(iva_unitario_calculado));
				twVentaProducto.setnTotalUnitario( DateTimeUtil.truncarDosDecimales(total_unitario_calculado));
				twVentaProducto.setnPrecioPartida( DateTimeUtil.truncarDosDecimales(precio_partida_calculado));
				twVentaProducto.setnIvaPartida( DateTimeUtil.truncarDosDecimales(iva_partida_calculado));
				twVentaProducto.setnTotalPartida( DateTimeUtil.truncarDosDecimales(partida_total_calculado));
				
				
				
		
		
		
	        return twVentaProducto;
		
	
	}
	
    public static Double truncaValor(Double numero) {

        BigDecimal bd = new BigDecimal(numero);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        System.out.println(bd.doubleValue());

        return bd.doubleValue();
    }

}
