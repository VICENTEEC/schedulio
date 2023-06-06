package es.mdef.schedulio.entidades;

import es.mdef.scheduliolib.Sala;

public class SalaConId extends RecursoConId implements Sala{
	private String nombreSala;

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}

	public Tipo getTipo() {
		return Tipo.Sala;
	}
}
