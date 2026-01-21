package es.library.springboot.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.BookRequestDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.exceptions.ValidateException;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Author;
import es.library.springboot.models.Book;
import es.library.springboot.models.Category;
import es.library.springboot.repositories.AuthorRepository;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.repositories.CategoryRepository;
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
	private final AuthorRepository autRepositorio;
	private final CategoryRepository catRepositorio;
	private final FileStorageService fileStorageService;
	private final StorageProperties storageProperties;
	
	@Transactional(readOnly = true)
	public BookResponseDTO obtenerLibro(Long id)
	{
		return bookRepositorio.findById(id)
				.map(bookMapper::toBookDTO)
				.orElseThrow(() ->
						new EntityNotFoundException("Book not found"));
	}
	
	@Transactional(readOnly = true)
	public PageResponse<BookResponseDTO> obtenerLibrosxCoincidencia(String name, int page, int size)
	{
		pageable = PageableService.getPageable(page, size, "tituloLibro");
		
        Page<Book> pageBooks = bookRepositorio.findByTituloLibroContaining(name, pageable);

        return pageMapper.toPageResponse(pageBooks, bookMapper::toBookDTO);
	}
	
	@Transactional(readOnly = true)
	public PageResponse<BookResponseDTO> obtenerLibrosxCategoriayAutor(String cat, String aut, int page, int size) 
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
	
	@Transactional
	public BookResponseDTO guardarLibro(BookRequestDTO libro, MultipartFile file)
	{
	    if (bookRepositorio.existsByTituloLibro(libro.tituloLibro())) {
	        throw new ValidateException("Book already exists");
	    }

	    Book book = bookMapper.toBookDTO(libro);

	    book.setAutor(getAuthor(libro.nombreAutor()));
	    book.setCategorias(getCategories(libro.categorias()));
	    updateBookCover(book, file);

	    return buildBookResponse(bookRepositorio.save(book));
	}
	
	@Transactional
	public BookResponseDTO modLibro(Long id, BookRequestDTO libro, MultipartFile file) 
	{
	    Book book = bookRepositorio.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Book not found"));

	    book.setTituloLibro(libro.tituloLibro());
	    book.setAnioPublicacion(libro.anioPublicacion());
	    book.setSinopsisLibro(libro.sinopsisLibro());
	    book.setEstadoLibro(libro.estadoLibro());

	    book.setAutor(getAuthor(libro.nombreAutor()));
	    book.setCategorias(getCategories(libro.categorias()));

	    updateBookCover(book, file);

	    return buildBookResponse(bookRepositorio.save(book));
	}
	
	@Transactional
	public void eliminarLibro(Long id) 
	{
	    Book libro = bookRepositorio.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Book not found"));

	    fileStorageService.deleteFileIfExists(
	            libro.getPortadaLibro(),
	            storageProperties.getBookPath()
	    );

	    bookRepositorio.delete(libro);
	}
	
	private Author getAuthor(String nombreAutor) 
	{
	    return autRepositorio.findByNombreAutor(nombreAutor)
	            .orElseThrow(() -> new EntityNotFoundException("Author not found"));
	}

	private List<Category> getCategories(List<String> nombres) {
	    List<Category> categorias = catRepositorio.findByNombreCategoriaIn(nombres);

	    if (categorias.size() != nombres.size()) {
	        throw new EntityNotFoundException("One or more categories not found");
	    }
	    return categorias;
	}
	
	private void updateBookCover(Book libro, MultipartFile file) 
	{
	    if (file == null || file.isEmpty()) return;

	    fileStorageService.deleteFileIfExists(
	            libro.getPortadaLibro(),
	            storageProperties.getBookPath()
	    );

	    String filename = fileStorageService.saveFile(
	            file,
	            storageProperties.getBookPath()
	    );

	    libro.setPortadaLibro(filename);
	}
	
	private BookResponseDTO buildBookResponse(Book book) 
	{
	    BookResponseDTO dto = bookMapper.toBookDTO(book);

	    return new BookResponseDTO(
	            dto.idLibro(),
	            dto.tituloLibro(),
	            dto.anioPublicacion(),
	            dto.sinopsisLibro(),
	            dto.estadoLibro(),
	            fileStorageService.buildPublicUrl(book.getPortadaLibro(),storageProperties.getBookPath()),
	            dto.nombreAutor(),
	            dto.categorias()
	    );
	}

}