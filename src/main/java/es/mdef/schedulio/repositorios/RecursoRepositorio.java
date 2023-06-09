package es.mdef.schedulio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.mdef.schedulio.entidades.RecursoConId;

public interface RecursoRepositorio extends JpaRepository<RecursoConId, Long> {

    @Query("SELECT COUNT(r) FROM RecursoConId r WHERE TYPE(r) = EmpleadoConId")
    Integer contarEmpleados();
}
