package es.mdef.schedulio.entidades;

import es.mdef.scheduliolib.UsuarioImpl;

public class UsuarioConId extends UsuarioImpl{

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
