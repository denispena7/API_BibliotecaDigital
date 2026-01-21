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
import es.library.springboot.DTOs.responses.ApiResponse;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController 
{
	private final BookService service;
	
	@PreAuthorize("hasAuthority('book:read')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<BookResponseDTO>> obtenerLibro(@PathVariable Long id)
	{
		BookResponseDTO book = service.obtenerLibro(id);
		
		return ResponseEntity.ok(
				ApiResponse.<BookResponseDTO>builder()
				.data(book)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("hasAuthority('book:read')")
	@GetMapping("/filters/{search}")
	public ResponseEntity<ApiResponse<PageResponse<BookResponseDTO>>> obtenerBusquedaLibros(
			@PathVariable String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> books = service.obtenerLibrosxCoincidencia(search, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<BookResponseDTO>>builder()
				.data(books)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("hasAuthority('book:read')")
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<BookResponseDTO>>> obtenerLibrosxCatyAut(
	        @RequestParam(required = false) String categoria,
	        @RequestParam(required = false) String autor,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> books = service.obtenerLibrosxCategoriayAutor(categoria, autor, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<BookResponseDTO>>builder()
				.data(books)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("hasAuthority('book:create')")
	@PostMapping(
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
		)
	public ResponseEntity<ApiResponse<BookResponseDTO>> altaLibro(
			@RequestPart("data") BookRequestDTO libro,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		BookResponseDTO book = service.guardarLibro(libro, file);
		
		return ResponseEntity.ok(
				ApiResponse.<BookResponseDTO>builder()
				.data(book)
                .ok(true)
                .message("Book created")
                .build()
			);
	}
	
	@PreAuthorize("hasAuthority('book:update')")
	@PutMapping(
			value = "/{id}", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE
		)
	public ResponseEntity<ApiResponse<BookResponseDTO>> actualizarLibro(
			@PathVariable Long id, 
			@RequestPart("data") @Valid BookRequestDTO libro,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		BookResponseDTO libroActualizado = service.modLibro(id, libro, file);
		
		return ResponseEntity.ok(
				ApiResponse.<BookResponseDTO>builder()
				.data(libroActualizado)
                .ok(true)
                .message("Book updated")
                .build()
			);
	}
	
	@PreAuthorize("hasAuthority('book:delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarLibro(@PathVariable Long id)
	{
		service.eliminarLibro(id);
	    return ResponseEntity.noContent().build();
	}
}