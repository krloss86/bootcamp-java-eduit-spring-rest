package ar.com.eduducactionit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.eduducactionit.entity.Orden;
import ar.com.eduducactionit.repository.OrdenRepository;
import ar.com.eduducactionit.service.OrdenService;

@Service
public class OrdenServiceImpl implements OrdenService {

	@Autowired
	private OrdenRepository repository;
	
	@Override
	public List<Orden> findAll() {
		return this.repository.findAll();
	}

}
