package es.mdef.schedulio.REST;

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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import es.mdef.scheduliolib.Cita;
import es.mdef.schedulio.SchedulioApplication;
import es.mdef.schedulio.entidades.CitaConId;
import es.mdef.schedulio.entidades.UsuarioConId;
import es.mdef.schedulio.repositorios.UsuarioRepositorio;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private final UsuarioRepositorio repositorio;
	private final UsuarioAssembler assembler;
	private final UsuarioListaAssembler listaAssembler;
	private final CitaListaAssembler citaListaAssembler;
	private final Logger log;
	
	public UsuarioController(UsuarioRepositorio repositorio, UsuarioAssembler assembler, UsuarioListaAssembler listaAssembler, CitaListaAssembler citaListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.citaListaAssembler = citaListaAssembler;
		log = SchedulioApplication.log;
	}
	
	@GetMapping("{id}")
	public UsuarioModel one(@PathVariable Long id) {
		UsuarioConId usuarioConId = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Recuperado " + usuarioConId);
		return assembler.toModel(usuarioConId);
	}
	
	@GetMapping
	public CollectionModel<UsuarioListaModel> all() {
		log.info("Recuperada lista de usuarios");
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	@GetMapping("{id}/citas")
	public CollectionModel<CitaListaModel> citasUsuario(@PathVariable Long id) {
		List<Cita> citas = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "usuario"))
				.getCitas();
		List<CitaConId> citasConIds = new ArrayList<>();
		for (Cita cit : citas)
		{
			citasConIds.add((CitaConId) cit);
		}
		
		return citaListaAssembler.toCollection(citasConIds);
	}
	
	@PostMapping
	public UsuarioModel add(@RequestBody UsuarioModel model) {
		UsuarioConId usuarioConId = repositorio.save(assembler.toEntity(model));
		log.info("Añadido " + usuarioConId);
		return assembler.toModel(usuarioConId);
	}
	
	@PutMapping("{id}")
	public UsuarioModel edit(@PathVariable Long id, @RequestBody UsuarioModel model) {
		UsuarioConId usuarioConId = repositorio.findById(id).map(usr -> {
			usr.setNombreDelCliente(model.getNombreDelCliente());
			usr.setApellidosDelCliente(model.getApellidosDelCliente());
			usr.setTelefonoDelCliente(model.getTelefonoDelCliente());
			usr.setEmailDelCliente(model.getEmailDelCliente());
			return repositorio.save(usr);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Actualizado " + usuarioConId);
		return assembler.toModel(usuarioConId);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado usuario " + id);
		repositorio.deleteById(id);
	}
	
}
