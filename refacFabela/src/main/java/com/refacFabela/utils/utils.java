package com.refacFabela.utils;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

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

}
