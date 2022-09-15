package io.baufest.ppmtool.exceptions;

public class InvalidLoginResponse {
	private String username;
	private String password;
	
	//Json Object what we want to return
	public InvalidLoginResponse() {
		super();
		this.username = "Invalid Username";
		this.password = "Invalid Password";
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
