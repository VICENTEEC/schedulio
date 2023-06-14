package es.mdef.schedulio.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.schedulio.entidades.CitaConId;
import es.mdef.schedulio.entidades.ServicioConId;
import es.mdef.schedulio.entidades.UsuarioConId;

@Component
public class CitaAssembler implements RepresentationModelAssembler<CitaConId, CitaModel> {

	public CitaModel toModel (CitaConId entity) {
		CitaModel model = new CitaModel();
		model.setEstado(entity.getEstado());
		model.setFecha(entity.getFecha());
		model.setHora(entity.getHora());
		model.setUsuarioConId((UsuarioConId) entity.getUsuario());
		model.setServicioConId((ServicioConId) entity.getServicio());
		model.add(
				linkTo(methodOn(CitaController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).one(((UsuarioConId) entity.getUsuario()).getId())).withRel("usuario"),
				linkTo(methodOn(ServicioController.class).one(((ServicioConId) entity.getServicio()).getId())).withRel("servicio")
				);
		return model;
	}
	
	public CitaConId toEntity(CitaModel model) {
		CitaConId citaConId = new CitaConId();
		citaConId.setEstado(model.getEstado());
		citaConId.setFecha(model.getFecha());
		citaConId.setHora(model.getHora());
		citaConId.setUsuario(model.getUsuarioConId());
		citaConId.setServicio(model.getServicioConId());
		return citaConId;
	}
}
