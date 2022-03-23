package com.refacFabela.dto;

import java.io.Serializable;

public class JwtDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
	
	
	
	
	
	
	
	public JwtDto(String token) {
		this.token = token;
		
	}

	public JwtDto() {
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
}
