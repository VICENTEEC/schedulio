package es.mdef.schedulio.REST;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.schedulio.SchedulioApplication;
import es.mdef.schedulio.entidades.RecursoConId;
import es.mdef.schedulio.entidades.ServicioConId;
import es.mdef.schedulio.repositorios.RecursoRepositorio;
import es.mdef.schedulio.repositorios.ServicioRepositorio;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/servicios")
public class ServicioController {
	private final ServicioRepositorio repositorio;
	private final ServicioAssembler assembler;
	private final ServicioListaAssembler listaAssembler;
	private final RecursoRepositorio recursoRepositorio;
	private final Logger log;

	public ServicioController(ServicioRepositorio repositorio, ServicioAssembler assembler, ServicioListaAssembler listaAssembler, RecursoRepositorio recursoRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.recursoRepositorio = recursoRepositorio;
		log = SchedulioApplication.log;
	}
	
	@GetMapping("{id}")
	public ServicioModel one(@PathVariable Long id) {
		ServicioConId servicio = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "servicio"));
		log.info("Recuperado " + servicio);
		return assembler.toModel(servicio);
	}
	
	@GetMapping
	public CollectionModel<ServicioListaModel> all() {
		log.info("Recuperada lista de servicios");
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
//	@GetMapping("{id}/citas")
//	public CollectionModel<CitaListaModel> citasServicio(@PathVariable Long id) {
//		List<CitaConId> citas = repositorio.findById(id)
//				.orElseThrow(() -> new RegisterNotFoundException(id, "servicio"))
//				.getCitas();
//		return CollectionModel.of(
//				citas.stream().map(cita -> citaListaAssembler.toModel(cita)).collect(Collectors.toList()),
//				linkTo(methodOn(ServicioController.class).one(id)).slash("citas").withSelfRel()
//				);
//	}
	
	@PostMapping
	public ServicioModel add(@RequestBody ServicioModel model) {
		ServicioConId servicioConId = repositorio.save(assembler.toEntity(model));
		log.info("Añadido " + servicioConId);
		return assembler.toModel(servicioConId);
	}
	
	@PutMapping("{id}")
	public ServicioModel edit(@PathVariable Long id, @RequestBody ServicioModel model) {
		ServicioConId servicioConId = repositorio.findById(id).map(usr -> {
			usr.setNombre(model.getNombre());
			usr.setSubservicio(model.getSubservicio());
			usr.setPrecio(model.getPrecio());
			return repositorio.save(usr);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "servicio"));
		log.info("Actualizado " + servicioConId);
		return assembler.toModel(servicioConId);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado servicio " + id);
		repositorio.deleteById(id);
	}
	
}
