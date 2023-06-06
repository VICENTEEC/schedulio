package es.mdef.schedulio.entidades;

import es.mdef.scheduliolib.ServicioImpl;

public class ServicioConId extends ServicioImpl{
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
