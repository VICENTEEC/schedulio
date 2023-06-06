package es.mdef.schedulio.REST;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import es.mdef.schedulio.SchedulioApplication;
import es.mdef.schedulio.entidades.CitaConId;
import es.mdef.schedulio.repositorios.CitaRepositorio;
import es.mdef.schedulio.repositorios.RecursoRepositorio;


//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/citas")
public class CitaController {
	private final CitaRepositorio repositorio;
	private final CitaAssembler assembler;
	private final CitaListaAssembler listaAssembler;
	private final RecursoRepositorio recursoRepositorio;
	private final Logger log;
	
	public CitaController(CitaRepositorio repositorio, CitaAssembler assembler, CitaListaAssembler listaAssembler, RecursoRepositorio recursoRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.recursoRepositorio = recursoRepositorio;
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
	    
	    Integer numEmpleados = recursoRepositorio.contarEmpleados();

	    // Primero comprobamos que para cada hora de cada dia si la cantidad de citas confirmadas es maypor o igual al numero de empleados
	    for(LocalTime hora : horario) {
	        Integer citasConfirmadas = repositorio.contarCitasConfirmadasPorFechaYHora(fecha, hora);

	        if(citasConfirmadas >= numEmpleados) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }

	        Integer citasManicura = repositorio.contarCitasPorFechaYServicio(fecha);
	        Integer cantidadMaterial = recursoRepositorio.obtenerCantidadMaterial();
	        Integer existeSanz = recursoRepositorio.existeEmpleadoSanz();
	        Integer existeSala = recursoRepositorio.contarTotalSalas();

	        // Si la cantidad de material es null lo pongo a 0
	        cantidadMaterial = cantidadMaterial != null ? cantidadMaterial : 0;

	        if(numEmpleados < 2 || existeSanz == 0 || existeSala == 0 || cantidadMaterial == 0) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }

	        // Si la cantidad de citas es mayor o igual que la cantidad de material
	        if(citasManicura >= cantidadMaterial) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }

	        // Si hay una cita para ese dia y hora con SERVICIO_ID 4 o 5 la hora se marca como no disponible.
	        Integer citaServicio4o5 = repositorio.contarManicurasPorFechaHoraYServicio(fecha, hora);
	        String disponibilidad = (citaServicio4o5 > 0) ? "noDisponible" : "disponible";
	        disponibilidadHorario.add(new HoraDisponibilidad(hora, disponibilidad));
	    }

	    return disponibilidadHorario;
	}
	
	@GetMapping("/disponibilidadDepilacion")
	public List<HoraDisponibilidad> obtenerDisponibilidadDepilacion(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
	    List<HoraDisponibilidad> disponibilidadHorario = new ArrayList<>();
	    List<LocalTime> horario = Horario.getHorario();
	    
	    Integer numEmpleados = recursoRepositorio.contarEmpleados();

	    // Primero comprobamos que para cada hora de cada dia si la cantidad de citas confirmadas es maypor o igual al numero de empleados
	    for(LocalTime hora : horario) {
	        Integer citasConfirmadas = repositorio.contarCitasConfirmadasPorFechaYHora(fecha, hora);

	        if(citasConfirmadas >= numEmpleados) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }

	        Integer totalSalas = recursoRepositorio.contarTotalSalas();
	        if(numEmpleados < 2 || totalSalas == 0) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }

	        Integer existeMartinez = recursoRepositorio.existeEmpleadoMartinez();

	        Integer citasDepilacion = repositorio.contarCitasPorFechaHoraYServicio(fecha, hora);
	        String disponibilidad = existeMartinez > 0 && citasDepilacion == 0 ? "disponible" : "noDisponible";
	        disponibilidadHorario.add(new HoraDisponibilidad(hora, disponibilidad));
	    }

	    return disponibilidadHorario;
	}
	
	@GetMapping("/disponibilidadPeluqueria")
	public List<HoraDisponibilidad> obtenerDisponibilidadPeluqueria(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
	    List<HoraDisponibilidad> disponibilidadHorario = new ArrayList<>();
	    List<LocalTime> horario = Horario.getHorario();

	    Integer numEmpleados = recursoRepositorio.contarEmpleados();

	    // Primero comprobamos que para cada hora de cada dia si la cantidad de citas confirmadas es maypor o igual al numero de empleados
	    for(LocalTime hora : horario) {
	        Integer citasConfirmadas = repositorio.contarCitasConfirmadasPorFechaYHora(fecha, hora);

	        if(citasConfirmadas >= numEmpleados) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }

	        if(numEmpleados == 0) {
	            disponibilidadHorario.add(new HoraDisponibilidad(hora, "noDisponible"));
	            continue;
	        }

	        Integer citasServicio = repositorio.contarCitasPorFechaHoraYServicioPeluqueria(fecha, hora);
	        String disponibilidad = citasServicio >= numEmpleados ? "noDisponible" : "disponible";
	        disponibilidadHorario.add(new HoraDisponibilidad(hora, disponibilidad));
	    }

	    return disponibilidadHorario;
	}
	
}
