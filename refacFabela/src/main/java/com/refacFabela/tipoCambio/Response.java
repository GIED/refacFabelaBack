package com.refacFabela.tipoCambio;

public class Response {
	private SeriesResponse bmx;

	public SeriesResponse getBmx() {
		return bmx;
	}

	public void setBmx(SeriesResponse bmx) {
		this.bmx = bmx;
	}

	@Override
	public String toString() {
		return "Response [bmx=" + bmx + "]";
	}
	
	

}
