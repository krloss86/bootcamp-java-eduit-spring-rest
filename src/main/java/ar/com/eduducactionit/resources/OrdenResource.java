package ar.com.eduducactionit.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ar.com.eduducactionit.dto.client.socio.SocioDTO;
import ar.com.eduducactionit.dto.orden.OrdenRequestDTO;
import ar.com.eduducactionit.entity.Cupon;
import ar.com.eduducactionit.entity.EstadoOrden;
import ar.com.eduducactionit.entity.Orden;
import ar.com.eduducactionit.entity.Socios;
import ar.com.eduducactionit.enums.EstadoOrdenEnum;
import ar.com.eduducactionit.service.OrdenService;

@RestController
public class OrdenResource {

	@Autowired
	private OrdenService ordenService;
	
	@Autowired
	private RestTemplate restTemplateClient;
	
	@Value("${spring.external.service-socios-url}")
	private String sociosPathUrlBase;
	
	//GET all
	@GetMapping(value="/orden", produces = "application/json")
	public ResponseEntity<List<Orden>> findAll() {
	
		List<Orden>  ordenes = this.ordenService.findAll();

		return ResponseEntity.ok(ordenes);
	}
	
	@PostMapping("/orden")
	public ResponseEntity<?> post(
			@Valid @RequestBody OrdenRequestDTO ordenRequestDto
			) throws URISyntaxException {
		
		//saber si existe el socio
		SocioDTO socioDto = restTemplateClient
			.getForObject(sociosPathUrlBase + "/socio/"+ordenRequestDto.getSocioId(),
				SocioDTO.class);
		
		if(socioDto == null) {
			return ResponseEntity.badRequest().build();
		}
		
		//convierto de dto a entity
		Orden newOrden = Orden.builder()
			.cupon(ordenRequestDto.getCuponId()!= null ? Cupon.builder().id(ordenRequestDto.getCuponId()).build() : null)
			.estado(EstadoOrden.builder().id(ordenRequestDto.getEstadoOrdenId()).build())
			.fechaCreacion(new Date())
			.montoTotal(ordenRequestDto.getMontoTotal())
			.socio(Socios.builder().id(ordenRequestDto.getSocioId()).build())
			.build();
			
		this.ordenService.create(newOrden);
		
		// /orden/1
		return ResponseEntity.created(new URI("/orden/"+newOrden.getId())).build();
	}
	
	@GetMapping(value="/orden/{id}",produces = "application/json")
	public ResponseEntity<Orden> get(
			@PathVariable(name="id", required = true)
			Long id
		) {
		
		Optional<Orden> ordenOptional = this.ordenService.getById(id);
		
		if(!ordenOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(ordenOptional.get());
	}
	
//	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value="/orden/{id}",produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> update(
			@PathVariable(name="id", required = true) 
			Long id,
			@RequestBody OrdenRequestDTO ordenRequestDto
		) {
		
		Optional<Orden> ordenOptional = this.ordenService.getById(id);
		
		if(!ordenOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		if(!id.equals(ordenRequestDto.getOrdenId())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ordenRequestDto); //409	
		}
				
		Orden ordenFromDb = ordenOptional.get();
		
		if(ordenFromDb.isEstadoFinal()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ordenRequestDto); //409
		}
		
		if(!ordenFromDb.getEstado().getId().equals(ordenRequestDto.getEstadoOrdenId())) {
			//set nuevo estado usando el builder de DTO
			ordenFromDb.setEstado(EstadoOrden.builder().id(ordenRequestDto.getEstadoOrdenId()).build());
		}
		
		//mas datos para actualizar		
		this.ordenService.update(ordenFromDb);
		
		//actualizo el cupon????
		
		return ResponseEntity.ok(ordenFromDb);
				
	}

	@DeleteMapping("/orden/{id}")
	public ResponseEntity<Orden> delete(
			@PathVariable(name = "id",required = true) 
			Long id
		){
		
		Optional<Orden> ordenOptional = this.ordenService.getById(id);
		
		if(!ordenOptional.isPresent()) {
			return ResponseEntity.ok().build();
		}
		
		Orden ordenFromDb = ordenOptional.get();
		if(ordenFromDb.isCancelada()) {
			return ResponseEntity.ok().build();
		}
		
		ordenFromDb.setEstado(EstadoOrden.builder().id(EstadoOrdenEnum.CANCELADA.getId()).build());
		
		this.ordenService.update(ordenFromDb);
		
		return ResponseEntity.ok(ordenFromDb);
	}
}
