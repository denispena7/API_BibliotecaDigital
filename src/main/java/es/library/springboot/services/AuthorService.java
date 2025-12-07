package es.library.springboot.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.library.springboot.DTOs.AuthorDTO;
import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.mapper.AuthorMapper;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Author;
import es.library.springboot.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService 
{
	private final AuthorRepository autRepositorio;
	private final AuthorMapper autMapper;
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	public List<AuthorDTO> obtenerAutores()
	{
		return autMapper.toAutDTOList(
				autRepositorio.findAll(Sort.by(Sort.Direction.ASC, "nombreAutor"))
		);
	}
	
	public PageResponse<AuthorDTO> obtenerAutoresPaginados(int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size, Sort.by("nombreAutor").ascending());
        Page<Author> pageAutores = autRepositorio.findAll(pageable);

        return pageMapper.toPageResponse(pageAutores, autMapper::toAutDTO);
	}
	
	public List<BookDTO> obtenerLibrosPorAutor(String nombreAutor) 
	{
	    return autRepositorio.findByNombreAutor(nombreAutor)
	            .map(author -> author.getLibros().stream()
	                    .map(bookMapper::toBookDTO)
	                    .toList())
	            .orElse(List.of());
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