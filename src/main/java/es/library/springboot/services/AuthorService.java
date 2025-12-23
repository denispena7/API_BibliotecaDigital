package es.library.springboot.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.library.springboot.DTOs.AuthorDTO;
import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.mapper.AuthorMapper;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Author;
import es.library.springboot.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService 
{
	private final AuthorRepository autRepositorio;
	private final AuthorMapper autMapper;
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	Pageable pageable;
	
	@Transactional(readOnly = true)
	public PageResponse<AuthorDTO> obtenerAutoresPaginados(int page, int size)
	{
		pageable = PageableService.getPageable(page, size, "nombreAutor");
		
        Page<Author> pageAutores = autRepositorio.findAll(pageable);

        return pageMapper.toPageResponse(pageAutores, autMapper::toAutDTO);
	}
	
	@Transactional(readOnly = true)
	public List<BookDTO> obtenerLibrosPorAutor(String nombreAutor) 
	{
	    Author autor = autRepositorio.findByNombreAutor(nombreAutor)
	            .orElseThrow(() ->
	                new EntityNotFoundException("Author not found"));

	    return autor.getLibros().stream()
	            .map(bookMapper::toBookDTO)
	            .toList();
	}
	
	public Author guardarAutor(Author autor)
	{
		return autRepositorio.save(autor);
	}
	
    public Author actualizar(Long id, Author autorDetalles) 
    {
        return autRepositorio.findById(id)
            .map(autor -> {
            	autor.setNombreAutor(autorDetalles.getNombreAutor());
            	autor.setNacionalidadAutor(autorDetalles.getNacionalidadAutor());
            	autor.setFechaNacimiento(autorDetalles.getFechaNacimiento());
                return autRepositorio.save(autor);
            })
            .orElseThrow(() -> new RuntimeException("Autor no encontrado con id " + id));
    }
}