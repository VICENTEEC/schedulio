package es.mdef.schedulio.repositorios;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.mdef.schedulio.entidades.CitaConId;

public interface CitaRepositorio extends JpaRepository <CitaConId, Long>{
	
	// Consulta implicada en la disponibilidad de las 3. Se cuentan todas las citas confirmadas en una fecha y horas concretas
	@Query("SELECT COUNT(c) FROM CitaConId c WHERE c.fecha = :fecha AND c.hora = :hora AND c.estado = 'confirmada'")
	Integer contarCitasConfirmadasPorFechaYHora(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);

	// Consulta implicada en la disponibilidad de manicura
    @Query("SELECT COUNT(c) FROM CitaConId c WHERE c.fecha = :fecha AND c.servicio.id IN (4, 5) AND c.estado = 'confirmada'")
    Integer contarCitasPorFechaYServicio(@Param("fecha") LocalDate fecha);
    
    // Consulta implicada en la disponibilidad según el empleado Martinez para la depilacion
    @Query("SELECT COUNT(c) FROM CitaConId c WHERE c.fecha = :fecha AND c.hora = :hora AND c.servicio.id IN (6, 7) AND c.estado = 'confirmada'")
    Integer contarCitasPorFechaHoraYServicio(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);
    
    @Query("SELECT COUNT(c) FROM CitaConId c WHERE c.fecha = :fecha AND c.hora = :hora AND c.servicio.id IN (1, 2, 3) AND c.estado = 'confirmada'")
    Integer contarCitasPorFechaHoraYServicioPeluqueria(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);
    
    // Consulta implicada en la disponibilidad de manicura según la cantidad de citas de tipo manicura
    @Query("SELECT COUNT(c) FROM CitaConId c WHERE c.fecha = :fecha AND c.hora = :hora AND c.servicio.id IN (4, 5) AND c.estado = 'confirmada'")
    Integer contarManicurasPorFechaHoraYServicio(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora);
}