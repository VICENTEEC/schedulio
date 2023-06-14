package es.mdef.schedulio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mdef.schedulio.entidades.ServicioConId;

@RepositoryRestResource(path = "servicios", collectionResourceRel = "servicios")
public interface ServicioRepositorio extends JpaRepository<ServicioConId, Long> {

	ServicioConId findFirstByNombre(String nombre);

}
