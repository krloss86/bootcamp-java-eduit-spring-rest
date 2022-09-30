package ar.com.eduducactionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.eduducactionit.entity.Users;

@Repository
/*
 * T es la entidad sobre la cual voy a trabajar(Entity)
 * ID: es el tipo de datos de la id del Entity
 */
public interface UsersRepository extends JpaRepository<Users, Long>{ //EX DAO
	//ctrl+o+o
	//select * from useres where username='dato' 
	Users findByUsername(String username);
	
	/*
	@Query(
		value="Select u.category from Users where u.id = 2",
		nativeQuery = true)
	UsersCategory getUser2();
	*/
}
