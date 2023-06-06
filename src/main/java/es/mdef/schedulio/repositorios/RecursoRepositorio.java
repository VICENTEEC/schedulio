package es.mdef.schedulio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.mdef.schedulio.entidades.RecursoConId;

public interface RecursoRepositorio extends JpaRepository<RecursoConId, Long> {
	
	// Consulta implicada en la disponibilidad según los materiales de manicura
	@Query("SELECT r.cantidad FROM RecursoConId r WHERE r.nombreMaterial = 'Bote manicura'")
	Integer obtenerCantidadMaterial();

	// Consulta implicada en la disponibilidad según el empleado Martinez para la depilacion
    @Query("SELECT COUNT(r) FROM RecursoConId r WHERE r.apellidos = 'Martinez' AND TYPE(r) = EmpleadoConId")
    Integer existeEmpleadoMartinez();
    
    // Consulta implicada en la disponibilidad para peluqueria
    @Query("SELECT COUNT(r) FROM RecursoConId r WHERE TYPE(r) = EmpleadoConId")
    Integer contarEmpleados();
    
    @Query("SELECT COUNT(r) FROM RecursoConId r WHERE TYPE(r) = SalaConId")
    Integer contarTotalSalas();
    
    // Manicura
    @Query("SELECT COUNT(r) FROM RecursoConId r WHERE r.apellidos = 'Sanz' AND TYPE(r) = EmpleadoConId")
    Integer existeEmpleadoSanz();
}
