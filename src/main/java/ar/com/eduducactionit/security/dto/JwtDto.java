package ar.com.eduducactionit.security.dto;

public class JwtDto {

	private String token;
	
	private String type = "Bearer";

	public JwtDto(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public String getType() {
		return type;
	}
	
}
