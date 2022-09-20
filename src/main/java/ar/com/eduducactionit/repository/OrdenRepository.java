package ar.com.eduducactionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.eduducactionit.entity.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long>{

}
