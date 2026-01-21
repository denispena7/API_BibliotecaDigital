package es.library.springboot.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.AuthorRequestDTO;
import es.library.springboot.DTOs.responses.AuthorResponseDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.exceptions.ValidateException;
import es.library.springboot.mapper.AuthorMapper;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Author;
import es.library.springboot.models.Book;
import es.library.springboot.repositories.AuthorRepository;
import es.library.springboot.repositories.BookRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService 
{
	private final AuthorRepository autRepositorio;
	private final BookRepository bookRepositorio;
	private final AuthorMapper autMapper;
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	Pageable pageable;
	private final FileStorageService fileStorageService;
	private final StorageProperties storageProperties;
	
	@Transactional(readOnly = true)
	public PageResponse<AuthorResponseDTO> obtenerAutoresPaginados(int page, int size)
	{
		pageable = PageableService.getPageable(page, size, "nombreAutor");
		
        Page<Author> pageAutores = autRepositorio.findAll(pageable);

        return pageMapper.toPageResponse(pageAutores, autMapper::toAutDTO);
	}
	
	@Transactional(readOnly = true)
	public PageResponse<BookResponseDTO> obtenerLibrosPorAutor(String nombreAutor, int page, int size) 
	{
	    Author autor = autRepositorio.findByNombreAutor(nombreAutor)
	            .orElseThrow(() ->
	                new EntityNotFoundException("Author not found"));
	    
		pageable = PageableService.getPageable(page, size, "tituloLibro");
		
        Page<Book> pageBooks = bookRepositorio.findByAutorNombreAutor(pageable, autor.getNombreAutor());

        return pageMapper.toPageResponse(pageBooks, bookMapper::toBookDTO);
	}
	
	@Transactional
	public AuthorResponseDTO guardarAutor(AuthorRequestDTO autor, MultipartFile file)
	{
	    if (autRepositorio.existsByNombreAutor(autor.nombreAutor())) {
	        throw new ValidateException("Author already exists");
	    }
	    
	    Author autEntity = autMapper.toAutEnt(autor);
	    
	    autEntity.setFechaNacimiento(DateUtils.toLocalDate(autor.fechaNacimiento()));
	    
	    if (file != null && !file.isEmpty()) 
	    {
	    	String filename = fileStorageService.saveFile(file, storageProperties.getAuthorPath());
	    	autEntity.setImagenAutor(filename);	 
	    }
	    
	    Author saved = autRepositorio.save(autEntity);
	    
	    return buildAuthorResponse(saved); 
	}
	
	@Transactional
	public AuthorResponseDTO actualizarAutor(Long id, AuthorRequestDTO autorDto, MultipartFile file) 
	{
		Author autor = autRepositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		
		autor.setNombreAutor(autorDto.nombreAutor());
		autor.setNacionalidadAutor(autorDto.nacionalidadAutor());
		autor.setFechaNacimiento(DateUtils.toLocalDate(autorDto.fechaNacimiento()));
		
	    updateAuthorImage(autor, file);

	    Author saved = autRepositorio.save(autor);
	    
	    return buildAuthorResponse(saved); 
	}
	
	@Transactional
	public void eliminarAutor(Long id) 
	{
		Author autor = autRepositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Author not found"));
		
		autRepositorio.delete(autor);
		
		fileStorageService.deleteFileIfExists(autor.getImagenAutor(), storageProperties.getAuthorPath());
	}
	
    private void updateAuthorImage(Author autor, MultipartFile file) 
    {
	    if (file == null || file.isEmpty()) return;

	    fileStorageService.deleteFileIfExists(
	    		autor.getImagenAutor(),
	            storageProperties.getAuthorPath()
	    );

	    String filename = fileStorageService.saveFile(
	            file,
	            storageProperties.getAuthorPath()
	    );

	    autor.setImagenAutor(filename);
		
	}

	private AuthorResponseDTO buildAuthorResponse(Author aut) 
    {
	    AuthorResponseDTO dto = autMapper.toAutDTO(aut);

	    return new AuthorResponseDTO(
	            dto.idAutor(),
	            dto.nombreAutor(),
	            dto.nacionalidadAutor(),
	            dto.fechaNacimiento(),
	            fileStorageService.buildPublicUrl(aut.getImagenAutor(),storageProperties.getAuthorPath()
	    ));
	}


}