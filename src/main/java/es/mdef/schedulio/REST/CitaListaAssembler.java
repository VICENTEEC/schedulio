package es.mdef.schedulio.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.schedulio.entidades.CitaConId;
import es.mdef.schedulio.entidades.ServicioConId;
import es.mdef.schedulio.entidades.UsuarioConId;

@Component
public class CitaListaAssembler implements RepresentationModelAssembler<CitaConId, CitaListaModel>{
	private final UsuarioAssembler usuarioAssembler;
	private final ServicioAssembler servicioAssembler;
	
	public CitaListaAssembler(UsuarioAssembler usuarioAssembler, ServicioAssembler servicioAssembler) {
		this.usuarioAssembler = usuarioAssembler;
		this.servicioAssembler = servicioAssembler;
	}
	
	@Override
	public CitaListaModel toModel(CitaConId entity) {
		CitaListaModel model = new CitaListaModel();
		model.setEstado(entity.getEstado());
		model.setFecha(entity.getFecha());
		model.setHora(entity.getHora());
		model.setUsuarioModel(usuarioAssembler.toModel((UsuarioConId) entity.getUsuario()));
		model.setServicioModel(servicioAssembler.toModel((ServicioConId) entity.getServicio()));
		model.add(
				linkTo(methodOn(CitaController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).one(((UsuarioConId) entity.getUsuario()).getId())).withRel("usuariodesdeCitaListAssembler"),
				linkTo(methodOn(ServicioController.class).one(((ServicioConId) entity.getServicio()).getId())).withRel("ServiciodesdeCitaListAssembler")
				);
		return model;
	}
	
	public CollectionModel<CitaListaModel> toCollection(List<CitaConId> lista) {
		CollectionModel<CitaListaModel> collection = CollectionModel.of(
				lista.stream().map(this::toModel).collect(Collectors.toList())
				);
		collection.add(
				linkTo(methodOn(CitaController.class).all()).withRel("citas")
				);
		return collection;
	}
}
