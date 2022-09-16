package io.baufest.ppmtool.payload;

public class JWTLoginSucessReponse {
	private boolean sucess;
	private String token;
	
	public JWTLoginSucessReponse(boolean sucess, String token) {
		this.sucess = sucess;
		this.token = token;
	}

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "JWTLoginSucessReponse{" +
        "sucess=" + sucess +
        ", token='" + token + '\'' +
        '}';
	}

}
