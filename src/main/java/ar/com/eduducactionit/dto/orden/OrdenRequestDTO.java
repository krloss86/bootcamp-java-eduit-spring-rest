package ar.com.eduducactionit.dto.orden;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenRequestDTO {
	
	@NotNull
	private Long ordenId;
	
	@NotNull
	@Min(value = 1)
	private Long estadoOrdenId;
	@NotNull
	private Long socioId;
	@NotNull
	@Min(value = 0)
	private Double montoTotal;
	
	private Long cuponId;
}
