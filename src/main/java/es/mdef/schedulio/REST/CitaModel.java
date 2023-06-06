package es.mdef.schedulio.REST;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import es.mdef.schedulio.entidades.ServicioConId;
import es.mdef.schedulio.entidades.UsuarioConId;

@Relation(itemRelation="cita")
public class CitaModel extends RepresentationModel<CitaModel>{

	private String estado;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime hora;

    @JsonBackReference("usuario")
    private UsuarioConId usuarioConId;

    @JsonBackReference("servicio")
    private ServicioConId servicioConId;
    
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public UsuarioConId getUsuarioConId() {
		return usuarioConId;
	}

	public void setUsuarioConId(UsuarioConId usuarioConId) {
		this.usuarioConId = usuarioConId;
	}

	public ServicioConId getServicioConId() {
		return servicioConId;
	}

	public void setServicioConId(ServicioConId servicioConId) {
		this.servicioConId = servicioConId;
	}
    
}
