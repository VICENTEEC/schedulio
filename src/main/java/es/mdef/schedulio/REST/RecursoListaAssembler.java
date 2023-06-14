package es.mdef.schedulio.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.schedulio.entidades.EmpleadoConId;
import es.mdef.schedulio.entidades.MaterialConId;
import es.mdef.schedulio.entidades.RecursoConId;
import es.mdef.schedulio.entidades.SalaConId;


@Component
public class RecursoListaAssembler implements RepresentationModelAssembler<RecursoConId, RecursoListaModel> {

	@Override
	public RecursoListaModel toModel(RecursoConId entity) {
	    RecursoListaModel model = new RecursoListaModel();
	    model.setTipo(entity.getTipo());
	
	    switch (entity.getTipo()) {
	    	case Material: {
	    		model.setNombreMaterial(((MaterialConId) entity).getNombreMaterial());
	    		model.setCantidad(((MaterialConId) entity).getCantidad());
	    		break;
	    	}
	    	case Empleado: {
	    		model.setTelefono(((EmpleadoConId) entity).getTelefono());
	    		model.setApellidos(((EmpleadoConId) entity).getApellidos());
	    		break;
	    	}
	    	case Sala: {
	    		model.setNombreSala(((SalaConId) entity).getNombreSala());
	    		break;
	    	}
	    }
	    model.setNombre(entity.getNombre());
	    model.add(
	            linkTo(methodOn(RecursoController.class).one(entity.getId())).withSelfRel()
	    );
	    return model;
	}
	
	public CollectionModel<RecursoListaModel> toCollection(List<RecursoConId> lista) {
		CollectionModel<RecursoListaModel> collection = CollectionModel.of(
				lista.stream().map(this::toModel).collect(Collectors.toList())
				);
		collection.add(
				linkTo(methodOn(RecursoController.class).all()).withRel("recursos")
				);
		return collection;
	}
}
