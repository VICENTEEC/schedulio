package es.mdef.schedulio.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.schedulio.entidades.ServicioConId;

@Component
public class ServicioAssembler implements RepresentationModelAssembler<ServicioConId, ServicioModel> {

	@Override
	public ServicioModel toModel(ServicioConId entity) {
		ServicioModel model = new ServicioModel();
		model.setNombre(entity.getNombre());
		model.setSubservicio(entity.getSubservicio());
		model.setPrecio(entity.getPrecio());
		model.add(
				linkTo(methodOn(ServicioController.class).one(entity.getId())).withSelfRel()
				);
		return model;
	}
	
	public ServicioConId toEntity(ServicioModel model) {
		ServicioConId servicioConId = new ServicioConId();
		servicioConId.setNombre(model.getNombre());
		servicioConId.setSubservicio(model.getSubservicio());
		servicioConId.setPrecio(model.getPrecio());
		
		return servicioConId;
	}
}
