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

import es.library.springboot.DTOs.requests.AuthorRequestDTO;
import es.library.springboot.DTOs.responses.WraperResponse;
import es.library.springboot.controllers.api.AuthorApi;
import es.library.springboot.controllers.base.BaseController;
import es.library.springboot.DTOs.responses.AuthorResponseDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.services.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
@Tag(name = "Authors", description = "CRUD operations for authors")
public class AuthorController extends BaseController implements AuthorApi
{
	private final AuthorService service;
	
	@Override
	@PreAuthorize("hasAuthority('author:read')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WraperResponse<PageResponse<AuthorResponseDTO>>> listarAutores(
			int page, int size)
	{
		PageResponse<AuthorResponseDTO> authors = service.obtenerAutoresPaginados(page, size);
		
		return ok(authors, "success");
	}
	

	@Override
	@PreAuthorize("hasAuthority('author:read')")
	@GetMapping(value = "/{nombreAutor}/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WraperResponse<PageResponse<BookResponseDTO>>> obtenerLibros(
			@PathVariable String nombreAutor,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> books = service.obtenerLibrosPorAutor(nombreAutor, page, size);
		
		return ok(books, "success");
	}
	
	
	@PreAuthorize("hasAuthority('author:create')")
	@PostMapping(
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		    produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<WraperResponse<AuthorResponseDTO>> altaAutor(
			@Valid @RequestPart("data") AuthorRequestDTO autor,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		AuthorResponseDTO author = service.guardarAutor(autor, file);

		 return created(author, "Author created");
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('author:update')")
	@PutMapping(
			value = "/{id}",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		    produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<WraperResponse<AuthorResponseDTO>> actualizarAutor(
			@PathVariable Long id,
			@Valid @RequestPart("data") AuthorRequestDTO autor,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		AuthorResponseDTO author = service.actualizarAutor(id, autor, file);
		
		return ok(author, "Author updated");
	}
	
	
	
	@PreAuthorize("hasAuthority('author:delete')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> bajaAutor(@PathVariable Long id)
	{
		service.eliminarAutor(id);
	    return noContent();
	}
}