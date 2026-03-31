package es.library.springboot.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.BookRequestDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.DTOs.responses.WraperResponse;
import es.library.springboot.controllers.api.BookApi;
import es.library.springboot.controllers.base.BaseController;
import es.library.springboot.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController extends BaseController implements BookApi
{
	private final BookService service;
	
	@Override
	@PreAuthorize("hasAuthority('book:read')")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WraperResponse<BookResponseDTO>> obtenerLibro(
			@PathVariable Long id)
	{
		BookResponseDTO book = service.obtenerLibro(id);
		
		return ok(book, "success");
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('book:read')")
	@GetMapping(value = "/filters/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WraperResponse<PageResponse<BookResponseDTO>>> obtenerBusquedaLibros(
			@PathVariable String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> books = service.obtenerLibrosxCoincidencia(search, page, size);
		
		return ok(books, "success");
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('book:read')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WraperResponse<PageResponse<BookResponseDTO>>> obtenerLibrosxCatyAut(
			@RequestParam(required = false) String categoria,
			@RequestParam(required = false) String autor,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> books = service.obtenerLibrosxCategoriayAutor(
				categoria, autor, page, size);
		
		return ok(books, "success");
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('book:create')")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WraperResponse<BookResponseDTO>> altaLibro(
			@RequestPart("data") BookRequestDTO libro,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		BookResponseDTO book = service.guardarLibro(libro, file);

        return created(book, "Book created");
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('book:update')")
	@PutMapping(
			value = "/{id}", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<WraperResponse<BookResponseDTO>> actualizarLibro(
			@PathVariable Long id, 
			@RequestPart("data") @Valid BookRequestDTO libro,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		BookResponseDTO libroActualizado = service.modLibro(id, libro, file);
		
		return ok(libroActualizado, "Book updated");
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('book:delete')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> eliminarLibro(@PathVariable Long id)
	{
		service.eliminarLibro(id);
	    return noContent();
	}
}