package es.mdef.schedulio.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.schedulio.entidades.UsuarioConId;

public interface UsuarioRepositorio extends JpaRepository<UsuarioConId, Long>{

}
