package com.refacFabela.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.refacFabela.model.TcProducto;
import com.refacFabela.model.TwCaja;
import com.refacFabela.model.TwMarcaDescuentoMayorista;
import com.refacFabela.model.TwVentasProducto;
import com.refacFabela.repository.CajaRepository;
import com.refacFabela.repository.TwMarcaDescuentoMayoristaRepository;

public  class utils {
	
	public static String filtroTipoCambio = "ValorCambio";
	public static String filtroIva = "ValorIva";
	public static Date fechaSistema = new Date();
	public static LocalDateTime today = LocalDateTime.now();
	public static LocalDateTime tomorrow = today.plusDays(1);

	public String sumarRestarDiasFecha(LocalDateTime fecha, int dias) {
	    // Sumar o restar los días
	    LocalDateTime nuevaFecha = fecha.plusDays(dias);

	    // Formatear a dd-MM-yyyy
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    return nuevaFecha.format(formatter);
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
	
	/**
	 * Calcula el precio para revendedores según su tipo.
	 * 
	 * tipoRevendedor:
	 *   1 = Cliente Regular (sin descuento)
	 *   2 = Revendedor (descuento directo de n_id_descuento)
	 *   3 = Mayorista (descuento mejorado + soporte para ganancia de marca especial)
	 * 
	 * Para mayoristas (tipo=3):
	 *   - Si la marca existe en tw_marca_descuento_mayorista:
	 *     Usa n_ganancia de la tabla en lugar de tc_productos.nIdGanancia
	 *     SIN descuento 10% extra (ganancia pura de la tabla)
	 *   - Si la marca NO existe:
	 *     Usa ganancia normal de tc_productos.nIdGanancia
	 *     CON descuento 10% extra del gap (comportamiento original)
	 */
	public TcProducto calcularPrecioRevendedor(TcProducto tcProducto, Double dolar, Long tipoRevendedor,
			TwMarcaDescuentoMayoristaRepository marcaDescuentoRepository) {
		BigDecimal precioPeso = BigDecimal.ZERO;
		BigDecimal precioPesofinalSinIva = BigDecimal.ZERO;
		BigDecimal precio_unitario_calculado = BigDecimal.ZERO;
		BigDecimal iva_unitario_calculado = BigDecimal.ZERO;
		BigDecimal total_unitario_calculado = BigDecimal.ZERO;
		BigDecimal iva = new BigDecimal("0.16");
		tcProducto.setbPrecioConvenioMayorista(Boolean.FALSE);
		tcProducto.setsMensajePrecioConvenio(null);

		// 1. Conversión de moneda
		if (tcProducto.getsMoneda().equals("USD")) {
			precioPeso = tcProducto.getnPrecio().multiply(BigDecimal.valueOf(dolar));
		} else {
			precioPeso = tcProducto.getnPrecio();
		}

		// 2. Aplicar ganancia (margen de utilidad)
		// Regla nueva: solo para tipo 3 y marcas registradas se reemplaza la ganancia del producto.
		BigDecimal gananciaAplicada = BigDecimal.valueOf(tcProducto.getTcGanancia().getnGanancia());

		if (tipoRevendedor != null && tipoRevendedor == 3 && tcProducto.getnIdMarca() != null && marcaDescuentoRepository != null) {
			// Mayorista: buscar si la marca tiene ganancia especial
			Optional<TwMarcaDescuentoMayorista> marcaDescuento = 
				marcaDescuentoRepository.findByNIdMarca(tcProducto.getnIdMarca());
			
			if (marcaDescuento.isPresent()) {
				// Usar ganancia de la marca mayorista
				gananciaAplicada = marcaDescuento.get().getnGanancia();
				tcProducto.setbPrecioConvenioMayorista(Boolean.TRUE);
				tcProducto.setsMensajePrecioConvenio("Precio especial por convenio");
			}
		}

		precioPesofinalSinIva = precioPeso.add(
			precioPeso.multiply(gananciaAplicada));

		// 3. Calcular precio unitario base (sin IVA) = precio con ganancia
		BigDecimal precioBase = DateTimeUtil.truncarDosDecimales(precioPesofinalSinIva);

		// 4. Calcular el descuento según tipo de revendedor
		Long nIdDescuento = tcProducto.getnIdDescuento() != null ? tcProducto.getnIdDescuento() : 0L;
		Long nIdGanancia = tcProducto.getTcGanancia().getnId();
		double descuentoPorcentaje;

		if (tipoRevendedor != null && tipoRevendedor == 3) {
			// Mayorista (tc_tipo_revendedor.n_id=3): mantiene lógica actual
			long extra = (long) Math.floor((nIdGanancia - nIdDescuento) * 0.10);
			if (extra > 0) {
				descuentoPorcentaje = (nIdDescuento + extra) / 100.0;
			} else {
				descuentoPorcentaje = nIdDescuento / 100.0;
			}
		} else if (tipoRevendedor != null && tipoRevendedor == 2) {
			// RE VENDEDOR (tc_tipo_revendedor.n_id=2): descuento directo
			descuentoPorcentaje = nIdDescuento / 100.0;
		} else {
			// REGULAR (tc_tipo_revendedor.n_id=1): sin descuento
			descuentoPorcentaje = 0.0;
		}

		// 5. Calcular precio original (sin descuento) con IVA para comparación
		BigDecimal ivaOriginal = DateTimeUtil.truncarDosDecimales(precioBase.multiply(iva));
		BigDecimal precioOriginalConIva = precioBase.add(ivaOriginal);

		// 6. Aplicar descuento al precio base
		BigDecimal precioConDescuento = precioBase.multiply(
			BigDecimal.ONE.subtract(BigDecimal.valueOf(descuentoPorcentaje)));
		precio_unitario_calculado = DateTimeUtil.truncarDosDecimales(precioConDescuento);

		// 7. Calcular IVA
		iva_unitario_calculado = DateTimeUtil.truncarDosDecimales(
			precio_unitario_calculado.multiply(iva));
		total_unitario_calculado = precio_unitario_calculado.add(iva_unitario_calculado);

		// 8. Setear precios en producto
		tcProducto.setnPrecioOriginal(precioOriginalConIva);
		tcProducto.setnPrecioPeso(total_unitario_calculado);
		tcProducto.setnPrecioSinIva(precio_unitario_calculado);
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
