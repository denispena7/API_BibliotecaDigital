package es.library.springboot.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> 
{
	Page<Book> findAllByOrderByTituloLibroAsc(Pageable pageable); 	

	Optional<Book> findByTituloLibro(String tituloLibro);
	
	@Query("SELECT b FROM Book b WHERE LOWER(b.tituloLibro) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Book> buscarPorCoincidencia(@Param("name") String name);

    Page<Book> findByAutorNombreAutor(Pageable pageable, String nombreAutor);

    Page<Book> findByCategoriasNombreCategoria(Pageable pageable, String nombreCategoria);

    Page<Book> findDistinctByCategorias_NombreCategoriaAndAutor_NombreAutor(
    		String nombreCategoria, String nombreAutor, Pageable pageable);
    
    List<Book> findByPrestamoIdPrestamo(Long idPrestamo);
}