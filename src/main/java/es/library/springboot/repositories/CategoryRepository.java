package es.library.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> 
{
	Optional<Category> findByNombreCategoria(String nombreCategoria);
}