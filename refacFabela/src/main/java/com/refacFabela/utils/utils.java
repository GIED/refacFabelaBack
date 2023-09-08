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
				
	        double precioPeso = 0;
	        double precioPesofinalSinIva = 0;
            double precio_unitario_calculado = 0;
		 	double iva_unitario_calculado = 0;
	        double total_unitario_calculado = 0;   	        
	        double iva=0.16;
	       
          
	        if (tcProducto.getsMoneda().equals("USD")) {
	            precioPeso = tcProducto.getnPrecio()  * dolar;
	        } else {
	            precioPeso =tcProducto.getnPrecio();
	            }      	
	        
	        if(descuento) {	
	        	if(tcProducto.getTcDescuento().getnId()>0) {
	        	tcProducto.setsProducto(tcProducto.getsProducto()+"-"+tcProducto.getTcDescuento().getnId()+"%");
	        	}
	        	precioPeso=precioPeso- (precioPeso* tcProducto.getTcDescuento().getnGanancia());
	        	
	        }
	        
	        
	        
	        precioPesofinalSinIva = (double) ((precioPeso * tcProducto.getTcGanancia().getnGanancia()) + precioPeso);           
	        if (aumento > 0) {
	            precioPesofinalSinIva = precioPesofinalSinIva + aumento;
	         }	          	        
	        precio_unitario_calculado = (double) (truncarDecimales(precioPesofinalSinIva));
	        iva_unitario_calculado = (double) (precio_unitario_calculado) * iva; 
	        total_unitario_calculado=precio_unitario_calculado + iva_unitario_calculado;      	            
	         tcProducto.setnPrecioPeso(truncarDecimales(total_unitario_calculado));
	         tcProducto.setnPrecioSinIva( truncarDecimales(precio_unitario_calculado));
	         tcProducto.setnPrecioConIva(truncarDecimales(total_unitario_calculado));
	         tcProducto.setnPrecioIva(truncarDecimales(iva_unitario_calculado));	         
	       
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
	        
	        

	        precio_unitario_calculado = (double) (truncarDecimales(tcProducto.getnPrecioSinIva()));
	        iva_unitario_calculado = (double) (precio_unitario_calculado) * iva; 
	        total_unitario_calculado=precio_unitario_calculado + iva_unitario_calculado;
	        precio_partida_calculado= precio_unitario_calculado *  cantidad;         
	        iva_partida_calculado=iva_unitario_calculado * cantidad;          
	        partida_total_calculado=total_unitario_calculado * cantidad;   
	                    
	         
	         
	         
	         	twVentaProducto.setnPrecioUnitario(truncarDecimales(precio_unitario_calculado));
				twVentaProducto.setnIvaUnitario(truncarDecimales(iva_unitario_calculado));
				twVentaProducto.setnTotalUnitario(truncarDecimales(total_unitario_calculado));
				twVentaProducto.setnPrecioPartida(truncarDecimales(precio_partida_calculado));
				twVentaProducto.setnIvaPartida(truncarDecimales(iva_partida_calculado));
				twVentaProducto.setnTotalPartida(truncarDecimales(partida_total_calculado));
				
				
				
		
		
		
	        return twVentaProducto;
		
	
	}
	
    public static Double truncaValor(Double numero) {

        BigDecimal bd = new BigDecimal(numero);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        System.out.println(bd.doubleValue());

        return bd.doubleValue();
    }

}
