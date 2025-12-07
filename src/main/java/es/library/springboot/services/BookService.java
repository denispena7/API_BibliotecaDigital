package es.library.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Book;
import es.library.springboot.repositories.BookRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService
{
	private final BookRepository bookRepositorio;
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	public PageResponse<BookDTO> obtenerLibrosPaginados(int page, int size)
	{
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> pageLibros = bookRepositorio.findAllByOrderByTituloLibroAsc(pageable);

        return pageMapper.toPageResponse(pageLibros, bookMapper::toBookDTO);
	}
	
	public Optional<BookDTO> obtenerLibro(String name)
	{
        return bookRepositorio.findByTituloLibro(name)
                .map(bookMapper::toBookDTO);
	}
	
	public List<BookDTO> obtenerLibrosxCoincidencia(String name)
	{
        return bookMapper.toBookDTOList(
        		bookRepositorio.buscarPorCoincidencia(name));
	}
	
	public PageResponse<BookDTO> obtenerLibrosxCategoriayAutor(String cat, String aut, int page, int size) 
	{
	    Pageable pageable = PageRequest.of(page, size, Sort.by("tituloLibro").ascending());

	    Page<Book> pageLibros;
		
	    if (cat.equals("Todas") && aut.equals("Todos"))
	    {
	    	pageLibros = bookRepositorio.findAll(pageable); 	
	    }
	    else if (cat.equals("Todas"))
	    {
	    	pageLibros = bookRepositorio
	                .findByAutorNombreAutor(pageable, aut);
	    }
	    else if (aut.equals("Todos"))
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