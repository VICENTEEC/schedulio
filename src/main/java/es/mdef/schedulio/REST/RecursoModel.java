package es.mdef.schedulio.REST;


import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.scheduliolib.RecursoImpl.Tipo;
import es.mdef.schedulio.entidades.ServicioConId;

@Relation(itemRelation="recurso")
public class RecursoModel extends RepresentationModel<RecursoModel>{
	private Tipo tipo;
	private String nombre;
	private String nombreMaterial;
	private int cantidad;
	private String telefono;
	private String apellidos;
	private String nombreSala;
	private List<ServicioConId> serviciosConIds;
	
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
	    this.tipo = Tipo.valueOf(tipo);
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
	public List<ServicioConId> getServiciosConIds() {
		return serviciosConIds;
	}
	public void setServiciosConIds(List<ServicioConId> serviciosConIds) {
		this.serviciosConIds = serviciosConIds;
	}


}
