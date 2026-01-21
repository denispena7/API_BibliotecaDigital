package es.library.springboot.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> 
{
	Page<Book> findAllByOrderByTituloLibroAsc(Pageable pageable); 	

	Optional<Book> findByTituloLibro(String tituloLibro);
	
	Page<Book> findByTituloLibroContaining(String tituloLibro, Pageable pageable);

    Page<Book> findByAutorNombreAutor(Pageable pageable, String nombreAutor);

    Page<Book> findByCategoriasNombreCategoria(Pageable pageable, String nombreCategoria);

    Page<Book> findDistinctByCategorias_NombreCategoriaAndAutor_NombreAutor(
    		String nombreCategoria, String nombreAutor, Pageable pageable);
    
    Page<Book> findByPrestamoIdPrestamo(Long idPrestamo, Pageable pageable);

	Page<Book> findByTituloLibro(String name, Pageable pageable);

	boolean existsByTituloLibro(String tituloLibro);

	boolean existsByIdLibro(Long id);

	List<Book> findByIdLibroIn(List<Integer> libros);
}