package es.mdef.schedulio.REST;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.scheduliolib.Servicio;
import es.mdef.schedulio.SchedulioApplication;
import es.mdef.schedulio.entidades.EmpleadoConId;
import es.mdef.schedulio.entidades.MaterialConId;
import es.mdef.schedulio.entidades.RecursoConId;
import es.mdef.schedulio.entidades.SalaConId;
import es.mdef.schedulio.entidades.ServicioConId;
import es.mdef.schedulio.repositorios.RecursoRepositorio;

@RestController
@RequestMapping("/recursos")
public class RecursoController {
	private final RecursoRepositorio repositorio;
	private final RecursoAssembler assembler;
	private final RecursoListaAssembler listaAssembler;
	private final ServicioListaAssembler servicioListaAssembler;
	private final Logger log;
	
	RecursoController(RecursoRepositorio repositorio, RecursoAssembler assembler, RecursoListaAssembler listaAssembler, ServicioListaAssembler servicioListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.servicioListaAssembler = servicioListaAssembler;
		log = SchedulioApplication.log;
	}
	
	@GetMapping("{id}")
	public RecursoModel one(@PathVariable Long id) {
		RecursoConId recursoConId = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "recurso"));
		log.info("Recuperado " + recursoConId);
		return assembler.toModel(recursoConId);
	}
	
	@GetMapping
	public CollectionModel<RecursoListaModel> all() {
		log.info("Lista de Recursos");
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	@GetMapping("{id}/servicios")
	public CollectionModel<ServicioListaModel> serviciosRecurso(@PathVariable Long id) {
		List<Servicio> servicios = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "recurso"))
				.getServicios();
		
		List<ServicioConId> serviciosConId = new ArrayList<>();
		for (Servicio serv : servicios)
		{
			serviciosConId.add((ServicioConId) serv);
		}
		
		return servicioListaAssembler.toCollection(serviciosConId);
	}
	
	@PostMapping
	public RecursoModel add(@RequestBody RecursoModel model) {
		RecursoConId recursoConId = repositorio.save(assembler.toEntity(model));
		log.info("Añadido " + recursoConId);
		return assembler.toModel(recursoConId);
	}
	
	@PutMapping("{id}")
	public RecursoModel edit(@PathVariable Long id, @RequestBody RecursoModel model) {
		RecursoConId recursoConId = repositorio.findById(id).map(usr -> {
			if (usr.getTipo() == null) {
			} else {
				switch (usr.getTipo()) {
				case Material: {
					((MaterialConId) usr).setNombreMaterial(model.getNombreMaterial());
					((MaterialConId) usr).setCantidad(model.getCantidad());
					break;
				}
				case Empleado: {
					((EmpleadoConId) usr).setTelefono(model.getTelefono());
					((EmpleadoConId) usr).setApellidos(model.getApellidos());
					break;
				}
				case Sala: {
					((SalaConId) usr).setNombreSala(model.getNombreSala());
					break;
				}
				}
			}
			usr.setNombre(model.getNombre());
			usr.setServicios(new ArrayList<>(model.getServiciosConIds()));
			return repositorio.save(usr);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "recurso"));
		log.info("Actualizado " + recursoConId);
		return assembler.toModel(recursoConId);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
	    RecursoConId recursoConId = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "recurso"));
	    recursoConId.getServicios().clear();
	    repositorio.save(recursoConId);       
	    repositorio.deleteById(id);           
	    log.info("Borrado recurso " + id);
	}
	
}