package com.refacFabela.repository;

import java.time.LocalDateTime;

public interface HistoriaIngresoProductoView {

    Long getnIdProducto();

    String getsFolioFactura();

    String getsRazonSocial();

    Integer getnCantidad();     // ajusta a Integer/Long si tu columna es INT

    LocalDateTime getdFechaIngreso();

    String getsUbicacion();

    String getsNombreUsuario();   
    
    
}
