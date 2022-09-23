package ar.com.eduducactionit.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ar.com.eduducactionit.dto.client.socio.SocioDTO;
import ar.com.eduducactionit.dto.orden.OrdenRequestDTO;
import ar.com.eduducactionit.entity.Cupon;
import ar.com.eduducactionit.entity.EstadoOrden;
import ar.com.eduducactionit.entity.Orden;
import ar.com.eduducactionit.entity.Socios;
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
		//http://localhost:8081/socio/ordenRequestDto.getSocioId() => Socio
//		RestTemplate restTemplateClient = new RestTemplate();
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
			
		this.ordenService.crear(newOrden);
		
		// /orden/1
		return ResponseEntity.created(new URI("/orden/"+newOrden.getId())).build();
	}
	
}
