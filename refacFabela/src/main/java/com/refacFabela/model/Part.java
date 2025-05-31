package com.refacFabela.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Part {
	   @JsonProperty("strPartNumber")
	    private String strPartNumber;

	    @JsonProperty("strDescrip1")
	    private String strDescrip1;

	    @JsonProperty("intWeigthPnd")
	    private String intWeigthPnd;

	    @JsonProperty("dblWeigthKgs")
	    private String dblWeigthKgs;

	    @JsonProperty("dblLengthIn")
	    private String dblLengthIn;

	    @JsonProperty("dblWidthIn")
	    private String dblWidthIn;

	    @JsonProperty("dblHeightIn")
	    private String dblHeightIn;

	    @JsonProperty("dblVolumeIn3")
	    private String dblVolumeIn3;

	    @JsonProperty("intPackQty")
	    private String intPackQty;

	    @JsonProperty("obsPackQty")
	    private String obsPackQty;

	    @JsonProperty("dblListPrice")
	    private String dblListPrice;

	    @JsonProperty("strFlgCtpPho")
	    private String strFlgCtpPho;

	    @JsonProperty("strFlgPrdItm")
	    private String strFlgPrdItm;

	    @JsonProperty("strFlgHazardM")
	    private String strFlgHazardM;

	    @JsonProperty("strFlgNonRet")
	    private String strFlgNonRet;

	    @JsonProperty("strMajorDsc")
	    private String strMajorDsc;

	    @JsonProperty("strCategoryDs")
	    private String strCategoryDs;

	    @JsonProperty("strSbCatDsc")
	    private String strSbCatDsc;

	    @JsonProperty("strMinorDsc")
	    private String strMinorDsc;

	    @JsonProperty("strHTSCCode")
	    private String strHTSCCode;

	    @JsonProperty("codError")
	    private String codError;

	    @JsonProperty("obsError")
	    private String obsError;

	    @JsonProperty("qteToken")
	    private String qteToken;
	    
    @JsonProperty("Locations")
    private Map<String, Location> locations;

    public Map<String, Location> getLocations() {
        return locations;
    }

    public void setLocations(Map<String, Location> locations) {
        this.locations = locations;
    }
    
    
	public String getStrPartNumber() {
		return strPartNumber;
	}
	public void setStrPartNumber(String strPartNumber) {
		this.strPartNumber = strPartNumber;
	}
	public String getStrDescrip1() {
		return strDescrip1;
	}
	public void setStrDescrip1(String strDescrip1) {
		this.strDescrip1 = strDescrip1;
	}
	public String getIntWeigthPnd() {
		return intWeigthPnd;
	}
	public void setIntWeigthPnd(String intWeigthPnd) {
		this.intWeigthPnd = intWeigthPnd;
	}
	public String getDblWeigthKgs() {
		return dblWeigthKgs;
	}
	public void setDblWeigthKgs(String dblWeigthKgs) {
		this.dblWeigthKgs = dblWeigthKgs;
	}
	public String getDblLengthIn() {
		return dblLengthIn;
	}
	public void setDblLengthIn(String dblLengthIn) {
		this.dblLengthIn = dblLengthIn;
	}
	public String getDblWidthIn() {
		return dblWidthIn;
	}
	public void setDblWidthIn(String dblWidthIn) {
		this.dblWidthIn = dblWidthIn;
	}
	public String getDblHeightIn() {
		return dblHeightIn;
	}
	public void setDblHeightIn(String dblHeightIn) {
		this.dblHeightIn = dblHeightIn;
	}
	public String getDblVolumeIn3() {
		return dblVolumeIn3;
	}
	public void setDblVolumeIn3(String dblVolumeIn3) {
		this.dblVolumeIn3 = dblVolumeIn3;
	}
	public String getIntPackQty() {
		return intPackQty;
	}
	public void setIntPackQty(String intPackQty) {
		this.intPackQty = intPackQty;
	}
	public String getObsPackQty() {
		return obsPackQty;
	}
	public void setObsPackQty(String obsPackQty) {
		this.obsPackQty = obsPackQty;
	}
	public String getDblListPrice() {
		return dblListPrice;
	}
	public void setDblListPrice(String dblListPrice) {
		this.dblListPrice = dblListPrice;
	}
	public String getStrFlgCtpPho() {
		return strFlgCtpPho;
	}
	public void setStrFlgCtpPho(String strFlgCtpPho) {
		this.strFlgCtpPho = strFlgCtpPho;
	}
	public String getStrFlgPrdItm() {
		return strFlgPrdItm;
	}
	public void setStrFlgPrdItm(String strFlgPrdItm) {
		this.strFlgPrdItm = strFlgPrdItm;
	}
	public String getStrFlgHazardM() {
		return strFlgHazardM;
	}
	public void setStrFlgHazardM(String strFlgHazardM) {
		this.strFlgHazardM = strFlgHazardM;
	}
	public String getStrFlgNonRet() {
		return strFlgNonRet;
	}
	public void setStrFlgNonRet(String strFlgNonRet) {
		this.strFlgNonRet = strFlgNonRet;
	}
	public String getStrMajorDsc() {
		return strMajorDsc;
	}
	public void setStrMajorDsc(String strMajorDsc) {
		this.strMajorDsc = strMajorDsc;
	}
	public String getStrCategoryDs() {
		return strCategoryDs;
	}
	public void setStrCategoryDs(String strCategoryDs) {
		this.strCategoryDs = strCategoryDs;
	}
	public String getStrSbCatDsc() {
		return strSbCatDsc;
	}
	public void setStrSbCatDsc(String strSbCatDsc) {
		this.strSbCatDsc = strSbCatDsc;
	}
	public String getStrMinorDsc() {
		return strMinorDsc;
	}
	public void setStrMinorDsc(String strMinorDsc) {
		this.strMinorDsc = strMinorDsc;
	}
	public String getStrHTSCCode() {
		return strHTSCCode;
	}
	public void setStrHTSCCode(String strHTSCCode) {
		this.strHTSCCode = strHTSCCode;
	}
	public String getCodError() {
		return codError;
	}
	public void setCodError(String codError) {
		this.codError = codError;
	}
	public String getObsError() {
		return obsError;
	}
	public void setObsError(String obsError) {
		this.obsError = obsError;
	}
	public String getQteToken() {
		return qteToken;
	}
	public void setQteToken(String qteToken) {
		this.qteToken = qteToken;
	}
	
	
	
	
	
	@Override
	public String toString() {
		return "Part [strPartNumber=" + strPartNumber + ", strDescrip1=" + strDescrip1 + ", intWeigthPnd="
				+ intWeigthPnd + ", dblWeigthKgs=" + dblWeigthKgs + ", dblLengthIn=" + dblLengthIn + ", dblWidthIn="
				+ dblWidthIn + ", dblHeightIn=" + dblHeightIn + ", dblVolumeIn3=" + dblVolumeIn3 + ", intPackQty="
				+ intPackQty + ", obsPackQty=" + obsPackQty + ", dblListPrice=" + dblListPrice + ", strFlgCtpPho="
				+ strFlgCtpPho + ", strFlgPrdItm=" + strFlgPrdItm + ", strFlgHazardM=" + strFlgHazardM
				+ ", strFlgNonRet=" + strFlgNonRet + ", strMajorDsc=" + strMajorDsc + ", strCategoryDs=" + strCategoryDs
				+ ", strSbCatDsc=" + strSbCatDsc + ", strMinorDsc=" + strMinorDsc + ", strHTSCCode=" + strHTSCCode
				+ ", codError=" + codError + ", obsError=" + obsError + ", qteToken=" + qteToken + "]";
	}
    
    
    
    

}
