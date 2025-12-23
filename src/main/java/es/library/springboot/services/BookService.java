package es.library.springboot.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Book;
import es.library.springboot.repositories.BookRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService
{
	private final BookRepository bookRepositorio;
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	Pageable pageable;
	
	@Transactional(readOnly = true)
	public BookDTO obtenerLibro(Long id)
	{
		return bookRepositorio.findById(id)
				.map(bookMapper::toBookDTO)
				.orElseThrow(() ->
						new EntityNotFoundException("Book not found"));
	}
	
	@Transactional(readOnly = true)
	public List<BookDTO> obtenerLibrosxCoincidencia(String name)
	{
        return bookRepositorio.findByTituloLibroContaining(name).stream()
        		.map(bookMapper::toBookDTO)
        		.toList();
	}
	
	@Transactional(readOnly = true)
	public PageResponse<BookDTO> obtenerLibrosxCategoriayAutor(String cat, String aut, int page, int size) 
	{
		pageable = PageableService.getPageable(page, size, "tituloLibro");

	    Page<Book> pageLibros;
	    
	    String categoria = (cat == null) ? "Todas" : cat;
	    String autor = (aut == null) ? "Todos" : aut;
		
	    if (categoria.equals("Todas") && autor.equals("Todos"))
	    {
	    	pageLibros = bookRepositorio.findAll(pageable); 	
	    }
	    else if (categoria.equals("Todas"))
	    {
	    	pageLibros = bookRepositorio
	                .findByAutorNombreAutor(pageable, aut);
	    }
	    else if (autor.equals("Todos"))
	    {
	    	pageLibros = bookRepositorio
	                .findByCategoriasNombreCategoria(pageable, cat);
	    }
	    else
	    {
	    	pageLibros = bookRepositorio
	                .findDistinctByCategorias_NombreCategoriaAndAutor_NombreAutor(cat, aut, pageable);	    	
	    }
	    
	    return pageMapper.toPageResponse(pageLibros, bookMapper::toBookDTO);
	}
	
	public Book guardarLibro(Book libro)
	{
		return bookRepositorio.save(libro);
	}
	
	public Book actualizar(Long id, Book libroDetalles) 
	{
	    return bookRepositorio.findById(id)
	        .map(libro -> {
	            libro.setTituloLibro(libroDetalles.getTituloLibro());
	            libro.setAnioPublicacion(libroDetalles.getAnioPublicacion());
	            libro.setEstadoLibro(libroDetalles.getEstadoLibro());
	            return bookRepositorio.save(libro);
	        })
	        .orElseThrow(() -> new RuntimeException("Libro no encontrado con id " + id));
	}
}