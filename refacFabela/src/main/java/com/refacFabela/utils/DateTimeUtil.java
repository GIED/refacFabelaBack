package com.refacFabela.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

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
    
 // ======= NUEVO: FORMATEO A STRING EN MX (dd/MM/yyyy HH:mm) =======
    private static final ZoneId ZONA_MX = ZoneId.of(ZONE_ID_MEXICO);
    private static final Locale LOCALE_MX = new Locale("es", "MX");
    private static final String PATRON_DDMMYYYY_HHMM = "dd/MM/yyyy HH:mm";
    private static final DateTimeFormatter FMT_MX =
            DateTimeFormatter.ofPattern(PATRON_DDMMYYYY_HHMM).withLocale(LOCALE_MX);

    /** LocalDateTime -> "dd/MM/yyyy HH:mm" (zona MX, truncado a minutos) */
    public static String formatearFechaHoraMx(LocalDateTime ldt) {
        if (ldt == null) return "";
        return ldt.truncatedTo(ChronoUnit.MINUTES).atZone(ZONA_MX).format(FMT_MX);
    }

    /** Date -> "dd/MM/yyyy HH:mm" (zona MX) */
    public static String formatearFechaHoraMx(Date date) {
        if (date == null) return "";
        return formatearFechaHoraMx(LocalDateTime.ofInstant(date.toInstant(), ZONA_MX));
    }

    /** Instant -> "dd/MM/yyyy HH:mm" (zona MX) */
    public static String formatearFechaHoraMx(Instant instant) {
        if (instant == null) return "";
        return formatearFechaHoraMx(LocalDateTime.ofInstant(instant, ZONA_MX));
    }

    /** LocalDate -> "dd/MM/yyyy HH:mm" a las 00:00 (zona MX) */
    public static String formatearFechaHoraMx(LocalDate ld) {
        if (ld == null) return "";
        return ld.atStartOfDay(ZONA_MX).format(FMT_MX);
    }

    /** Variante con patrón personalizado (si algún día lo necesitas) */
    public static String formatearFechaHoraMx(LocalDateTime ldt, String patron) {
        if (ldt == null) return "";
        DateTimeFormatter fmt = DateTimeFormatter
                .ofPattern((patron == null || patron.isEmpty()) ? PATRON_DDMMYYYY_HHMM : patron)
                .withLocale(LOCALE_MX);
        return ldt.atZone(ZONA_MX).format(fmt);
    }
    
    
    
    
    
}