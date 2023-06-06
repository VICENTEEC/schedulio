package es.mdef.schedulio.entidades;

import es.mdef.scheduliolib.Material;

public class MaterialConId extends RecursoConId implements Material{
	
	private String nombreMaterial;
	private int cantidad;
	public String getNombreMaterial() {
		return nombreMaterial;
	}
	public void setNombreMaterial(String nombreMaterial) {
		this.nombreMaterial = nombreMaterial;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public Tipo getTipo() {
		return Tipo.Material;
	}
	
}
