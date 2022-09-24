package ar.com.eduducactionit.service;

import java.util.List;
import java.util.Optional;

import ar.com.eduducactionit.entity.Orden;

public interface OrdenService {

	public List<Orden> findAll();

	public Orden create(Orden newOrden);

	public Optional<Orden> getById(Long id);

	public void update(Orden ordenFromDb);
}
