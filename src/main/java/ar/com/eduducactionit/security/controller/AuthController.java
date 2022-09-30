package ar.com.eduducactionit.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.eduducactionit.security.JwtProvider;
import ar.com.eduducactionit.security.dto.JwtDto;
import ar.com.eduducactionit.security.dto.LoginRequestDto;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
		
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping(value="/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<JwtDto> login (
			@Valid @RequestBody LoginRequestDto loginRequestDto
			) {
		
		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
				loginRequestDto.getUsername(), loginRequestDto.getPassword());
		
		Authentication authentication =  this.authenticationManager.authenticate(userToken);
		
		String jwt = jwtProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JwtDto(jwt));
	}
}
