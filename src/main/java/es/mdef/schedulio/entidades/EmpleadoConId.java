package es.mdef.schedulio.entidades;

import es.mdef.scheduliolib.Empleado;

public class EmpleadoConId extends RecursoConId implements Empleado{
	
	private String telefono;
	private String apellidos;
	
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public Tipo getTipo() {
		return Tipo.Empleado;
	}
	
}
