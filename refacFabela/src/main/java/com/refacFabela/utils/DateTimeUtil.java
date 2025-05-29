package com.refacFabela.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

public class DateTimeUtil {

    private static final String ZONE_ID_MEXICO = "America/Mexico_City";

  
    public static LocalDateTime obtenerHoraExactaDeMexico() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(ZONE_ID_MEXICO));
        return zonedDateTime.toLocalDateTime();
    }
    
    public static Date convertirALocalDateTimeEnMexicoADate(LocalDateTime fechaLocalDateTime) {
        ZoneId zonaMexico = ZoneId.of(ZONE_ID_MEXICO); // Debe estar definido como constante
        return Date.from(fechaLocalDateTime.atZone(zonaMexico).toInstant());
    }
    
    
    public static String convertirFecha(LocalDateTime fecha) {
        if (fecha == null) {
            return null;
        }

        // Truncar a minutos
        LocalDateTime truncada = fecha.truncatedTo(ChronoUnit.MINUTES);

        // Convertir a java.util.Date
        Date fechaDate = Date.from(truncada.atZone(ZoneId.systemDefault()).toInstant());

        // Formatear como "dd/MM/yyyy HH:mm"
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatter.format(fechaDate);
    }
    public static BigDecimal truncarDosDecimales(BigDecimal valor) {
        if (valor == null) {
            return BigDecimal.ZERO;
        }
        return valor.setScale(2, RoundingMode.DOWN); // truncado sin redondeo
    }
}