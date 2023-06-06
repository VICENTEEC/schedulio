package es.mdef.schedulio.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.schedulio.entidades.UsuarioConId;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<UsuarioConId, UsuarioModel>{

	@Override
	public UsuarioModel toModel(UsuarioConId entity) {
		UsuarioModel model = new UsuarioModel();
		model.setNombreDelCliente(entity.getNombreDelCliente());
		model.setApellidosDelCliente(entity.getApellidosDelCliente());
		model.setTelefonoDelCliente(entity.getTelefonoDelCliente());
		model.setEmailDelCliente(entity.getEmailDelCliente());
		model.add(
				linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel()
				);
		return model;
	}
	
	public UsuarioConId toEntity(UsuarioModel model) {
		UsuarioConId usuarioConId = new UsuarioConId();
		usuarioConId.setNombreDelCliente(model.getNombreDelCliente());
		usuarioConId.setApellidosDelCliente(model.getApellidosDelCliente());
		usuarioConId.setTelefonoDelCliente(model.getTelefonoDelCliente());
		usuarioConId.setEmailDelCliente(model.getEmailDelCliente());
		
		return usuarioConId;
	}
}
