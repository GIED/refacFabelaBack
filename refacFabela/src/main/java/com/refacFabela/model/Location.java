package com.refacFabela.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
	
	@JsonProperty("LocCode")
    private String locCode;

    @JsonProperty("BranchName")
    private String branchName;

    @JsonProperty("BranchState")
    private String branchState;

    @JsonProperty("BranchZip")
    private String branchZip;

    @JsonProperty("BranchTaxPr")
    private String branchTaxPr;

    @JsonProperty("NetQtyStock")
    private String netQtyStock;

    @JsonProperty("PotProductQ")
    private String potProductQ;

    @JsonProperty("ExpecDatePr")
    private String expecDatePr;

    @JsonProperty("NxtExpPopD")
    private String nxtExpPopD;

    @JsonProperty("CustPrice")
    private String custPrice;
    
    @JsonProperty("InventoryStatus")
    private String inventoryStatus;

    // Getters y Setters
    
    
    
    
    public String getLocCode() {
        return locCode;
    }

    public String getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchState() {
        return branchState;
    }

    public void setBranchState(String branchState) {
        this.branchState = branchState;
    }

    public String getBranchZip() {
        return branchZip;
    }

    public void setBranchZip(String branchZip) {
        this.branchZip = branchZip;
    }

    public String getBranchTaxPr() {
        return branchTaxPr;
    }

    public void setBranchTaxPr(String branchTaxPr) {
        this.branchTaxPr = branchTaxPr;
    }

    public String getNetQtyStock() {
        return netQtyStock;
    }

    public void setNetQtyStock(String netQtyStock) {
        this.netQtyStock = netQtyStock;
    }

    public String getPotProductQ() {
        return potProductQ;
    }

    public void setPotProductQ(String potProductQ) {
        this.potProductQ = potProductQ;
    }

    public String getExpecDatePr() {
        return expecDatePr;
    }

    public void setExpecDatePr(String expecDatePr) {
        this.expecDatePr = expecDatePr;
    }

    public String getNxtExpPopD() {
        return nxtExpPopD;
    }

    public void setNxtExpPopD(String nxtExpPopD) {
        this.nxtExpPopD = nxtExpPopD;
    }

    public String getCustPrice() {
        return custPrice;
    }

    public void setCustPrice(String custPrice) {
        this.custPrice = custPrice;
    }

	@Override
	public String toString() {
		return "Location [locCode=" + locCode + ", branchName=" + branchName + ", branchState=" + branchState
				+ ", branchZip=" + branchZip + ", branchTaxPr=" + branchTaxPr + ", netQtyStock=" + netQtyStock
				+ ", potProductQ=" + potProductQ + ", expecDatePr=" + expecDatePr + ", nxtExpPopD=" + nxtExpPopD
				+ ", custPrice=" + custPrice + "]";
	}
		
	    

}
