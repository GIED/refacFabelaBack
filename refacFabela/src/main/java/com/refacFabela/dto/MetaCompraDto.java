package com.refacFabela.dto;

import java.math.BigDecimal;

public class MetaCompraDto {
    private final String sNoParte;
    private final String sProducto;
    private final String sMarca;
    private final BigDecimal nTotalUnitarioCalculado;

    public MetaCompraDto(String sNoParte, String sProducto, String sMarca, BigDecimal nTotalUnitarioCalculado) {
        this.sNoParte = sNoParte;
        this.sProducto = sProducto;
        this.sMarca = sMarca;
        this.nTotalUnitarioCalculado = nTotalUnitarioCalculado;
    }

    public String getsNoParte() { return sNoParte; }
    public String getsProducto() { return sProducto; }
    public String getsMarca() { return sMarca; }
    public BigDecimal getnTotalUnitarioCalculado() { return nTotalUnitarioCalculado; }
}
