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
import es.library.springboot.DTOs.responses.ApiResponse;
import es.library.springboot.DTOs.responses.AuthorResponseDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.services.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
public class AuthorController 
{
	private final AuthorService service;
	
	@PreAuthorize("hasAuthority('author:read')")
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<AuthorResponseDTO>>> listarAutores(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<AuthorResponseDTO> authors = service.obtenerAutoresPaginados(page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<AuthorResponseDTO>>builder()
				.data(authors)
				.ok(true)
				.message("success")
				.build());
	}

	@PreAuthorize("hasAuthority('author:read')")
	@GetMapping(value = "/{nombreAutor}/books")
	public ResponseEntity<ApiResponse<PageResponse<BookResponseDTO>>> obtenerLibros(
			@PathVariable String nombreAutor,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> books = service.obtenerLibrosPorAutor(nombreAutor, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<BookResponseDTO>>builder()
					.data(books)
	                .ok(true)
	                .message("success")
	                .build()
				);
	}
	
	@PreAuthorize("hasAuthority('author:create')")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<AuthorResponseDTO>> altaAutor(
			@Valid @RequestPart("data") AuthorRequestDTO autor,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		AuthorResponseDTO author = service.guardarAutor(autor, file);
		
		return ResponseEntity.ok(
				ApiResponse.<AuthorResponseDTO>builder()
					.data(author)
	                .ok(true)
	                .message("Author created")
	                .build()
				);
	}
	
	@PreAuthorize("hasAuthority('author:update')")
	@PutMapping(
			value = "/{id}",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ResponseEntity<ApiResponse<AuthorResponseDTO>> actualizarAutor(
			@PathVariable Long id, 
			@Valid @RequestPart("data") AuthorRequestDTO autor,
			@RequestPart(value = "file", required = false) MultipartFile file
	)
	{
		AuthorResponseDTO author = service.actualizarAutor(id, autor, file);
		
		return ResponseEntity.ok(
				ApiResponse.<AuthorResponseDTO>builder()
					.data(author)
	                .ok(true)
	                .message("Author updated")
	                .build()
				);
	}
	
	@PreAuthorize("hasAuthority('author:delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> bajaAutor(@PathVariable Long id)
	{
		service.eliminarAutor(id);
	    return ResponseEntity.noContent().build();
	}
}