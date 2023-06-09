package es.mdef.schedulio.repositorios;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.mdef.schedulio.entidades.CitaConId;

public interface CitaRepositorio extends JpaRepository <CitaConId, Long>{
	
	@Query("SELECT COUNT(c) FROM CitaConId c WHERE c.fecha = :fecha AND c.hora = :hora AND c.estado = 'confirmada'")
	Integer contarCitasConfirmadasPorFechaYHora(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);

    @Query("SELECT COUNT(c) FROM CitaConId c WHERE c.fecha = :fecha AND c.hora = :hora AND c.servicio.id IN (1, 2, 3) AND c.estado = 'confirmada'")
    Integer contarCitasPorFechaHoraYServicioPeluqueria(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);
   
    @Query("SELECT COUNT(c) FROM CitaConId c WHERE c.fecha = :fecha AND c.hora = :hora AND c.servicio.id IN (4, 5) AND c.estado = 'confirmada'")
    Integer contarManicurasPorFechaHoraYServicio(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);
    
    @Query("SELECT COUNT(c) FROM CitaConId c WHERE c.fecha = :fecha AND c.hora = :hora AND c.servicio.id IN (6, 7) AND c.estado = 'confirmada'")
    Integer contarCitasPorFechaHoraYServicioDepilacion(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);
}