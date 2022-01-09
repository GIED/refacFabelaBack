package com.refacFabela.utils;

import java.time.LocalDateTime;
import java.util.Date;

public  class utils {
	
	public static String filtroTipoCambio="ValorCambio";
	public static String filtroIva="ValorIva";
	public static Date fechaSistema=(new Date());
	public static LocalDateTime today = LocalDateTime.now();
	public static LocalDateTime tomorrow = today.plusDays(1); 
	


}
