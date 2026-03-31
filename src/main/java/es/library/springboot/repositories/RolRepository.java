package es.library.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.library.springboot.config.Role;
import es.library.springboot.models.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> 
{
	Optional<Rol> findByNombreRol(Role nombreRol); 
}