package es.library.springboot.controllers;

import org.springframework.http.HttpStatus;
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
import es.library.springboot.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "CRUD operations for books")
public class BookController 
{
	private final BookService service;
	
	@PreAuthorize("hasAuthority('book:read')")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "GET A BOOK",
    		description = "Returns a book by its id"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Book returned successfully"),
    		@ApiResponse(responseCode = "404", description = "Book not found"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<BookResponseDTO>> obtenerLibro(
			@Parameter(description = "Book ID", required = true) @PathVariable Long id)
	{
		BookResponseDTO book = service.obtenerLibro(id);
		
		return ResponseEntity.ok(
				WraperResponse.<BookResponseDTO>builder()
				.data(book)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("hasAuthority('book:read')")
	@GetMapping(value = "/filters/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "GET BOOKS WITH A KEYWORD",
    		description = "Returns a paginated list of books with a keyword"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Books returned successfully"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<PageResponse<BookResponseDTO>>> obtenerBusquedaLibros(
			@Parameter(description = "Keyword", required = true) @PathVariable String search,
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> books = service.obtenerLibrosxCoincidencia(search, page, size);
		
		return ResponseEntity.ok(
				WraperResponse.<PageResponse<BookResponseDTO>>builder()
				.data(books)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("hasAuthority('book:read')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "GET BOOKS WITH FILTERS",
    		description = "Returns a paginated list of books with filters"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Books returned successfully"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<PageResponse<BookResponseDTO>>> obtenerLibrosxCatyAut(
			@Parameter(description = "Category Name", required = true) @RequestParam(required = false) String categoria,
			@Parameter(description = "Author Name", required = true) @RequestParam(required = false) String autor,
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> books = service.obtenerLibrosxCategoriayAutor(categoria, autor, page, size);
		
		return ResponseEntity.ok(
				WraperResponse.<PageResponse<BookResponseDTO>>builder()
				.data(books)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("hasAuthority('book:create')")
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "CREATE BOOKS",
    		description = "Create a new book"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201", description = "Book created successfully"),
    		@ApiResponse(responseCode = "400", description = "A field type is incorrect"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<BookResponseDTO>> altaLibro(
		    @Parameter(
		            description = "Book data (JSON)",
		            required = true,
		            content = @Content(
		                mediaType = MediaType.APPLICATION_JSON_VALUE,
		                schema = @Schema(implementation = BookRequestDTO.class)
		            )
		        )
			@RequestPart("data") BookRequestDTO libro,
			@Parameter(description = "Optional book image", required = true) @RequestPart(value = "file", required = false) MultipartFile file)
	{
		BookResponseDTO book = service.guardarLibro(libro, file);

        WraperResponse<BookResponseDTO> response =
                WraperResponse.<BookResponseDTO>builder()
                        .data(book)
                        .ok(true)
                        .message("Book created")
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
	}
	
	@PreAuthorize("hasAuthority('book:update')")
	@PutMapping(
			value = "/{id}", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
		)
    @Operation(
    		summary = "UPDATE BOOKS",
    		description = "Update a book"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Book updated successfully"),
    		@ApiResponse(responseCode = "400", description = "A field type is incorrect"),
    		@ApiResponse(responseCode = "404", description = "Book not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<BookResponseDTO>> actualizarLibro(
			@Parameter(description = "Book ID", required = true) @PathVariable Long id, 
		    @Parameter(
		            description = "Book data (JSON)",
		            required = true,
		            content = @Content(
		                mediaType = MediaType.APPLICATION_JSON_VALUE,
		                schema = @Schema(implementation = BookRequestDTO.class)
		            )
		        )
			@RequestPart("data") @Valid BookRequestDTO libro,
			@Parameter(description = "Optional book image", required = true) @RequestPart(value = "file", required = false) MultipartFile file)
	{
		BookResponseDTO libroActualizado = service.modLibro(id, libro, file);
		
		return ResponseEntity.ok(
				WraperResponse.<BookResponseDTO>builder()
				.data(libroActualizado)
                .ok(true)
                .message("Book updated")
                .build()
			);
	}
	
	@PreAuthorize("hasAuthority('book:delete')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "DELETE BOOKS",
    		description = "Delete a book"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "204", description = "Book deleted successfully"),
    		@ApiResponse(responseCode = "404", description = "Book not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<Void> eliminarLibro(@Parameter(description = "Book ID", required = true) @PathVariable Long id)
	{
		service.eliminarLibro(id);
	    return ResponseEntity.noContent().build();
	}
}