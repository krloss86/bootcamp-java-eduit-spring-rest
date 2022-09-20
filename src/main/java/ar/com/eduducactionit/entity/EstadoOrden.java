package ar.com.eduducactionit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="estados_ordenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EstadoOrden {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="descripcion", nullable = false, length = 45)
	private String descripcion;
	
	@Column(name="descripcion_corta", nullable = true, length = 45)
	private String descripcionCorta;
	
	@Column(name="estado_final", nullable = false)
	private Long estadoFinal;
}
