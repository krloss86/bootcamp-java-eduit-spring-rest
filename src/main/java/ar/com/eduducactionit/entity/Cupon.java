package ar.com.eduducactionit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cupones")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;//?
	
	@NotEmpty
	@Column(name="nombre", length = 100)
	private String nombre;
	@Column(name="codigo", length = 10,unique = true)
	private String codigo;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha_vigencia_desde", nullable = false)//no acepta nulos
	private Date fechaVigenciaDesde;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha_vigencia_hasta", nullable = true)//si acepta nulos
	private Date fechaVigenciaHasta;
	
	@NotNull
	@Min(value = 1)
	@Max(value = 100)
	@Column(name="descuento",nullable = false)
	private Double descuento;
	
}
