package es.mdef.schedulio.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.scheduliolib.RecursoImpl.Tipo;


@Relation(collectionRelation = "recursos")
public class RecursoListaModel extends RepresentationModel<RecursoListaModel> {
    private Tipo tipo;
    private String nombre;
    private String nombreMaterial;
    private int cantidad;
    private String telefono;
    private String apellidos;
    private String nombreSala;
    
    public Tipo getTipo() {
		return tipo;
	}
    
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
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
	
	public String getNombreSala() {
		return nombreSala;
	}
	
	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}	
}
