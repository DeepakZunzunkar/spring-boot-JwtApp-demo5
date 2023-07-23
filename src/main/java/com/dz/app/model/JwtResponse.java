package com.dz.app.model;

public class JwtResponse {
 
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "JwtResponse [token=" + token + "]";
	}

	public JwtResponse() {
		super();
	}

	public JwtResponse(String token) {
		this.token=token;
	}
	
}
