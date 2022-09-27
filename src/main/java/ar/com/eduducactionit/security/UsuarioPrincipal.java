package ar.com.eduducactionit.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ar.com.eduducactionit.entity.Users;

public class UsuarioPrincipal implements UserDetails{

	private String username;
	private String password;
	private Collection<GrantedAuthority> authorities;

	
	public UsuarioPrincipal(String username, String password, Collection<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public UsuarioPrincipal build(Users users) {
		
		Set<GrantedAuthority> auths = users.getRoles()
			.stream()
			.map(r -> new SimpleGrantedAuthority("ROLE_"+r.getRole()))
			.collect(Collectors.toSet());
		//@TODO podria quitar el segundo parametros
		return new UsuarioPrincipal(users.getUsername(),users.getPassword(), auths);
	}


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
