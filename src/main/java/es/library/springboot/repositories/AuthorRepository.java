package es.library.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>
{
	Optional<Author> findByNombreAutor(String nombreAutor);

	boolean existsByNombreAutor(String nombreAutor);
}