package ar.com.eduducactionit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ordenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Orden {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="fecha_creacion", nullable = false)//no acepta nulos
	private Date fechaCreacion;
	
	@ManyToOne
	@JoinColumn(name="socios_id", referencedColumnName = "id" )
	private Socios socio;
	
	@ManyToOne
	@JoinColumn(name="estados_ordenes_id", referencedColumnName = "id" )
	private EstadoOrden estado;
	
	@Column(name="monto_total",nullable = false)
	private Double montoTotal;
	
	@OneToOne
	@JoinColumn(name="cupones_id", referencedColumnName = "id")
	private Cupon cupon;
	
	/*
	public boolean isEstadoFinal() {
		
	}
	*/
}
