package ar.com.eduducactionit.security.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.com.eduducactionit.entity.Users;


@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UsersService service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = this.service.buscarPorNombreDeUsuario(username);
		//roles > son grant authority -> SimpleGrantedAuthority
		
		Set<GrantedAuthority> auths = users.getRoles()
			.stream()
			.map(r -> new SimpleGrantedAuthority(r.getRole()))
			.collect(Collectors.toSet());
			
		//UserDetail
		/*String username, String password, boolean enabled, boolean accountNonExpired,
		boolean credentialsNonExpired, boolean accountNonLocked,
		Collection<? extends GrantedAuthority> authorities*/
		return new User(users.getUsername(), users.getPassword(), auths);
	}

}
