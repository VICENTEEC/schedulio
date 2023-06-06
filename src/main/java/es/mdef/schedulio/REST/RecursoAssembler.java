package es.mdef.schedulio.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.schedulio.entidades.EmpleadoConId;
import es.mdef.schedulio.entidades.MaterialConId;
import es.mdef.schedulio.entidades.RecursoConId;
import es.mdef.schedulio.entidades.SalaConId;


@Component
public class RecursoAssembler implements RepresentationModelAssembler<RecursoConId, RecursoModel>{
	
	public RecursoModel toModel(RecursoConId entity) {
		RecursoModel model = new RecursoModel();
		
		switch(entity.getTipo()) {
			case Material: {
				model.setNombreMaterial(((MaterialConId)entity).getNombreMaterial());;;
				model.setCantidad(((MaterialConId)entity).getCantidad());
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
		model.setTipo(entity.getTipo().name());
		model.setNombre(entity.getNombre());
		model.add(
				linkTo(methodOn(RecursoController.class).one(entity.getId())).withSelfRel(),
	            linkTo(methodOn(RecursoController.class).serviciosRecurso(entity.getId())).withRel("serviciosDesdeRecursoAssem")
				);
		return model;
	}
	
	public RecursoConId toEntity(RecursoModel model) {
		RecursoConId recursoConId;
		
		switch (model.getTipo()) {
		case Material: {
			MaterialConId materialConId = new MaterialConId();
			
			materialConId.setNombreMaterial(model.getNombreMaterial());
			materialConId.setCantidad(model.getCantidad());
			
			recursoConId = materialConId;
			break;
		}
		case Empleado: {
			EmpleadoConId empleadoConId = new EmpleadoConId();
			
			empleadoConId.setTelefono(model.getTelefono());
			empleadoConId.setApellidos(model.getApellidos());
			
			recursoConId = empleadoConId;
			break;
		}
		case Sala: {
			SalaConId salaConId = new SalaConId();
			salaConId.setNombreSala(model.getNombreSala());
			recursoConId = salaConId;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + model.getTipo());
	}
		recursoConId.setNombre(model.getNombre());
		recursoConId.setServicios(new ArrayList<>(model.getServiciosConIds()));
		return recursoConId;
	}

}

