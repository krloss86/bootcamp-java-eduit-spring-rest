package ar.com.eduducactionit.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.eduducactionit.entity.Users;
import ar.com.eduducactionit.repository.UsersRepository;

@Service
public class UsersService {

	//D.I
	@Autowired
	private UsersRepository repository;

	public List<Users> buscarTodos() {
		return this.repository.findAll();
	}

	/*
	 * https://www.baeldung.com/spring-data-derived-queries
	 **/
	public Users buscarPorNombreDeUsuario(String username) {
		return this.repository.findByUsername(username);
	}

	public void crear(Users user) {
		this.repository.save(user);		
	}
	
	
}
