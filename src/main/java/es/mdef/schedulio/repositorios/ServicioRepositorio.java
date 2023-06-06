package es.mdef.schedulio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.schedulio.entidades.ServicioConId;

public interface ServicioRepositorio extends JpaRepository<ServicioConId, Long>{

}
