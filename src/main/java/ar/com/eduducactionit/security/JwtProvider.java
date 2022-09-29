package ar.com.eduducactionit.security;

import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtProvider {

	private Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	//generar usamos el expiration
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(this.jwtSecret)
				.parseClaimsJws(token);
			return true;
		}catch (ExpiredJwtException |MalformedJwtException| SignatureException e) {
			logger.error("Parseando token...",e);
			return false;
		}
	}

	/*
	 * pre-condicion: validateToken() > true*/
	public String getNombreUsuarioFromToken(String token) {
		return Jwts.parser()
		.setSigningKey(this.jwtSecret)
		.parseClaimsJws(token)
		.getBody()
		.getSubject();
	}

	public String generateToken(Authentication authentication) {
		
		User userPrincipal = (User)authentication.getPrincipal();
		
		Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
		claims.put("roles", userPrincipal.getAuthorities().stream().map(x->"ROLE_"+x.getAuthority()).collect(Collectors.toList()));
		
		return Jwts.builder()
			.setIssuedAt(new Date())
			.setExpiration(new Date(new Date().getTime() + this.expiration * 1000))
			.signWith(SignatureAlgorithm.HS512, this.jwtSecret)
			.setClaims(claims)
			.compact()
			;
	}
}
