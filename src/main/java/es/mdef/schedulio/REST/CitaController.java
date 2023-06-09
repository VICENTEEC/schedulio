package es.mdef.schedulio.REST;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.schedulio.entidades.HoraDisponibilidad;
import es.mdef.schedulio.entidades.Horario;
import es.mdef.schedulio.entidades.MaterialConId;
import es.mdef.schedulio.entidades.RecursoConId;
import es.mdef.schedulio.entidades.SalaConId;
import es.mdef.schedulio.entidades.ServicioConId;
import es.mdef.schedulio.SchedulioApplication;
import es.mdef.schedulio.entidades.CitaConId;
import es.mdef.schedulio.entidades.EmpleadoConId;
import es.mdef.schedulio.repositorios.CitaRepositorio;
import es.mdef.schedulio.repositorios.RecursoRepositorio;
import es.mdef.schedulio.repositorios.ServicioRepositorio;

@RestController
@RequestMapping("/citas")
public class CitaController {
	private final CitaRepositorio repositorio;
	private final CitaAssembler assembler;
	private final CitaListaAssembler listaAssembler;
	private final RecursoRepositorio recursoRepositorio;
	private final ServicioRepositorio servicioRepositorio;
	private final Logger log;
	
	public CitaController(CitaRepositorio repositorio, CitaAssembler assembler, CitaListaAssembler listaAssembler, RecursoRepositorio recursoRepositorio, ServicioRepositorio servicioRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.recursoRepositorio = recursoRepositorio;
		this.servicioRepositorio = servicioRepositorio;
		log = SchedulioApplication.log;
	}
	
	@GetMapping("{id}")
	public CitaModel one(@PathVariable Long id) {
		CitaConId citaConId = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "cita"));
		log.info("Recuperada " + citaConId);
		return assembler.toModel(citaConId);
	}
	
	@GetMapping
	public CollectionModel<CitaListaModel> all() {
		log.info("Recuperada lista de citas");
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	@PostMapping
	public CitaModel add(@RequestBody CitaModel model) {
		CitaConId citaConId = repositorio.save(assembler.toEntity(model));
		log.info("Añadida " + citaConId);
		return assembler.toModel(citaConId);
	}
	
	@PutMapping("{id}")
	public CitaModel edit(@PathVariable Long id, @RequestBody CitaModel model) {
		CitaConId citaConId = repositorio.findById(id).map(usr -> {
			usr.setEstado(model.getEstado());
			usr.setFecha(model.getFecha());
			usr.setHora(model.getHora());
			usr.setUsuario(model.getUsuarioConId());
			usr.setServicio(model.getServicioConId());
			return repositorio.save(usr);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "cita"));
		log.info("Actualizada " + citaConId);
		return assembler.toModel(citaConId);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrada cita " + id);
		repositorio.deleteById(id);
	}
	
	@GetMapping("/disponibilidadManicura")
	public List<HoraDisponibilidad> obtenerDisponibilidad(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
	    List<HoraDisponibilidad> disponibilidadHorario = new ArrayList<>();
	    List<LocalTime> horario = Horario.getHorario();
	    
	    ServicioConId servicioManicura = servicioRepositorio.findFirstByNombre("manicura");
	    List<RecursoConId> recursos = recursoRepositorio.findAll();
	    
	    List<EmpleadoConId> empleadosManicuraConIds = new ArrayList<>();
	    for (RecursoConId recursoConId : recursos) {
	    	if (recursoConId instanceof EmpleadoConId empl) {
	    		if (empl.getServicios().contains(servicioManicura)) {
	    			empleadosManicuraConIds.add(empl);
	    		}
	    	}
	    }
	    
	    List<SalaConId> salasManicuraConIds = new ArrayList<>();
	    for (RecursoConId recursoConId : recursos) {
	    	if (recursoConId instanceof SalaConId sala) {
	    		if (sala.getServicios().contains(servicioManicura)) {
	    			salasManicuraConIds.add(sala);
	    		}
	    	}
	    }
	    
	    List<MaterialConId> materialesManicuraConIds = new ArrayList<>();
	    for (RecursoConId recursoConId : recursos) {
	    	if (recursoConId instanceof MaterialConId material) {
	    		if (material.getServicios().contains(servicioManicura)) {
	    			materialesManicuraConIds.add(material);
	    		}
	    	}
	    }
	    
	    Integer numEmpleados = empleadosManicuraConIds.size();
	    Integer numSalasInteger = salasManicuraConIds.size();
	    Integer numMateriales = materialesManicuraConIds.size();
	    Integer numEmpleadosTotales = recursoRepositorio.contarEmpleados();

	    for(LocalTime hora : horario) {
	        Integer citasServicio = repositorio.contarManicurasPorFechaHoraYServicio(fecha, hora);
	        Integer citasTotales = repositorio.contarCitasConfirmadasPorFechaYHora(fecha, hora);
	        
	        if(citasTotales >= numEmpleadosTotales) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }
	        
	        if(citasServicio >= numSalasInteger || numSalasInteger == 0 || numMateriales == 0) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
		            continue;
		        }
	        
	        String disponibilidad = citasServicio >= numEmpleados ? "noDisponible" : "disponible";
	        disponibilidadHorario.add(new HoraDisponibilidad(hora, disponibilidad));
	    }
	    return disponibilidadHorario;
	}
	
	@GetMapping("/disponibilidadDepilacion")
	public List<HoraDisponibilidad> obtenerDisponibilidadDepilacion(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
	    List<HoraDisponibilidad> disponibilidadHorario = new ArrayList<>();
	    List<LocalTime> horario = Horario.getHorario();
	    
	    ServicioConId servicioDepilacion = servicioRepositorio.findFirstByNombre("depilacion");
	    List<RecursoConId> recursos = recursoRepositorio.findAll();
	    
	    List<EmpleadoConId> empleadosDepilacionConIds = new ArrayList<>();
	    for (RecursoConId recursoConId : recursos) {
	    	if (recursoConId instanceof EmpleadoConId empl) {
	    		if (empl.getServicios().contains(servicioDepilacion)) {
	    			empleadosDepilacionConIds.add(empl);
	    		}
	    	}
	    }
	    
	    List<SalaConId> salasDepilacionConIds = new ArrayList<>();
	    for (RecursoConId recursoConId : recursos) {
	    	if (recursoConId instanceof SalaConId sala) {
	    		if (sala.getServicios().contains(servicioDepilacion)) {
	    			salasDepilacionConIds.add(sala);
	    		}
	    	}
	    }
	    
	    List<MaterialConId> materialesDepilacionConIds = new ArrayList<>();
	    for (RecursoConId recursoConId : recursos) {
	    	if (recursoConId instanceof MaterialConId material) {
	    		if (material.getServicios().contains(servicioDepilacion)) {
	    			materialesDepilacionConIds.add(material);
	    		}
	    	}
	    }
	    
	    Integer numEmpleados = empleadosDepilacionConIds.size();
	    Integer numSalasInteger = salasDepilacionConIds.size();
	    Integer numMateriales = materialesDepilacionConIds.size();
	    Integer numEmpleadosTotales = recursoRepositorio.contarEmpleados();
	    
	    for(LocalTime hora : horario) {
	        Integer citasServicio = repositorio.contarCitasPorFechaHoraYServicioDepilacion(fecha, hora);
	        Integer citasTotales = repositorio.contarCitasConfirmadasPorFechaYHora(fecha, hora);
	        
	        if(citasTotales >= numEmpleadosTotales) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }
	        
	        if(citasServicio >= numSalasInteger || numSalasInteger == 0 || numMateriales == 0) {
            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }
	        
	        String disponibilidad = citasServicio >= numEmpleados ? "noDisponible" : "disponible";
	        disponibilidadHorario.add(new HoraDisponibilidad(hora, disponibilidad));
	    }
	    return disponibilidadHorario;
	}

	@GetMapping("/disponibilidadPeluqueria")
	public List<HoraDisponibilidad> obtenerDisponibilidadPeluqueria(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
	    List<HoraDisponibilidad> disponibilidadHorario = new ArrayList<>();
	    List<LocalTime> horario = Horario.getHorario();

	    ServicioConId servicioPeluqueria = servicioRepositorio.findFirstByNombre("peluqueria");
	    List<RecursoConId> recursos = recursoRepositorio.findAll();
	    List<EmpleadoConId> empleadosPeluqueriaConIds = new ArrayList<>();
	    
	    for (RecursoConId recursoConId : recursos) {
	    	if (recursoConId instanceof EmpleadoConId empl) {
	    		if (empl.getServicios().contains(servicioPeluqueria)) {
	    			empleadosPeluqueriaConIds.add(empl);
	    		}
	    	}
	    }
	    
	    Integer numEmpleados = empleadosPeluqueriaConIds.size();
	    Integer numEmpleadosTotales = recursoRepositorio.contarEmpleados();
	    
	    for(LocalTime hora : horario) {
	        Integer citasServicio = repositorio.contarCitasPorFechaHoraYServicioPeluqueria(fecha, hora);
	        Integer citasTotales = repositorio.contarCitasConfirmadasPorFechaYHora(fecha, hora);

	        if(citasTotales >= numEmpleadosTotales) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }
	        
	        String disponibilidad = citasServicio >= numEmpleados ? "noDisponible" : "disponible";
	        disponibilidadHorario.add(new HoraDisponibilidad(hora, disponibilidad));
	    }
	    return disponibilidadHorario;
	}
}
