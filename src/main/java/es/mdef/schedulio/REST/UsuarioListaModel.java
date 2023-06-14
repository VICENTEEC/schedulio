package es.mdef.schedulio.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
public class UsuarioListaModel extends RepresentationModel<UsuarioListaModel> {
	private Long id;
	private String nombreDelCliente;
	private String apellidosDelCliente;
	private String telefonoDelCliente;
	private String emailDelCliente;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombreDelCliente() {
		return nombreDelCliente;
	}
	
	public void setNombreDelCliente(String nombreDelCliente) {
		this.nombreDelCliente = nombreDelCliente;
	}
	
	public String getApellidosDelCliente() {
		return apellidosDelCliente;
	}
	
	public void setApellidosDelCliente(String apellidosDelCliente) {
		this.apellidosDelCliente = apellidosDelCliente;
	}
	
	public String getTelefonoDelCliente() {
		return telefonoDelCliente;
	}
	
	public void setTelefonoDelCliente(String telefonoDelCliente) {
		this.telefonoDelCliente = telefonoDelCliente;
	}
	
	public String getEmailDelCliente() {
		return emailDelCliente;
	}
	
	public void setEmailDelCliente(String emailDelCliente) {
		this.emailDelCliente = emailDelCliente;
	}
}
