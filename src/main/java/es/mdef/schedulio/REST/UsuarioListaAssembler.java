package es.mdef.schedulio.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.schedulio.entidades.UsuarioConId;

@Component
public class UsuarioListaAssembler implements RepresentationModelAssembler<UsuarioConId, UsuarioListaModel> {

	@Override
	public UsuarioListaModel toModel(UsuarioConId entity) {
		UsuarioListaModel model = new UsuarioListaModel();
		model.setId(entity.getId());
		model.setNombreDelCliente(entity.getNombreDelCliente());
		model.setApellidosDelCliente(entity.getApellidosDelCliente());
		model.setTelefonoDelCliente(entity.getTelefonoDelCliente());
		model.setEmailDelCliente(entity.getEmailDelCliente());
		model.add(
				linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel()
				);
		return model;
	}
	
	public CollectionModel<UsuarioListaModel> toCollection(List<UsuarioConId> lista) {
		CollectionModel<UsuarioListaModel> collection = CollectionModel.of(
				lista.stream().map(this::toModel).collect(Collectors.toList())
				);
		collection.add(
				linkTo(methodOn(UsuarioController.class).all()).withRel("usuarios")
				);
		return collection;
	}
}
