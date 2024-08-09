package com.refacFabela.tipoCambio;

import java.util.List;

public class SeriesResponse {

	private List<Serie>series;

	public List<Serie> getSeries() {
		return series;
	}

	public void setSeries(List<Serie> series) {
		this.series = series;
	}

	@Override
	public String toString() {
		return "SeriesResponse [series=" + series + "]";
	}
	
	
	
}