package ar.com.eduducactionit.enums;

public enum EstadoOrdenEnum {

	CANCELADA(3l), 
	ESTADO_FINAL(1l);
	
	private Long id;
	
	private EstadoOrdenEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
}
