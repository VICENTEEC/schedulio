package es.mdef.schedulio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mdef.schedulio.entidades.UsuarioConId;

@RepositoryRestResource(path = "usuarios", collectionResourceRel = "usuarios")
public interface UsuarioRepositorio extends JpaRepository<UsuarioConId, Long> {

}
