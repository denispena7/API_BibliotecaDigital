package es.library.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> 
{
	Optional<User> findByNombreUsuario(String nombreUsuario);
}