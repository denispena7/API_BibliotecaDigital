package es.library.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> 
{
	Optional<User> findByEmailUsuario(String emailUsuario);
	boolean existsByEmailUsuario(String emailUsuario);
	Optional<User> findByIdUsuario(Long id);
}