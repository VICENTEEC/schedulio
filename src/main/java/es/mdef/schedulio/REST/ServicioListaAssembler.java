package es.mdef.schedulio.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.schedulio.entidades.ServicioConId;
@Component
public class ServicioListaAssembler implements RepresentationModelAssembler<ServicioConId, ServicioListaModel>{

	@Override
	public ServicioListaModel toModel(ServicioConId entity) {
		ServicioListaModel model = new ServicioListaModel();
		model.setNombre(entity.getNombre());
		model.setSubservicio(entity.getSubservicio());
		model.setPrecio(entity.getPrecio());
		model.add(
				linkTo(methodOn(ServicioController.class).one(entity.getId())).withSelfRel()
				);
		return model;
	}
	
	public CollectionModel<ServicioListaModel> toCollection(List<ServicioConId> lista) {
		CollectionModel<ServicioListaModel> collection = CollectionModel.of(
				lista.stream().map(this::toModel).collect(Collectors.toList())
				);
		collection.add(
				linkTo(methodOn(ServicioController.class).all()).withRel("servicios")
				);
		return collection;
	}
}
