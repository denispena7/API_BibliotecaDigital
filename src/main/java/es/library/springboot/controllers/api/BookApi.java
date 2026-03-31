package es.library.springboot.controllers.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.BookRequestDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.DTOs.responses.WraperResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Books", description = "CRUD operations for books")
public interface BookApi 
{
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
			@Parameter(description = "Book ID", required = true) @PathVariable Long id
	);
	
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
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);
	

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
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);
    
    
	
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
			@Parameter(description = "Optional book image", required = true) @RequestPart(value = "file", required = false) MultipartFile file
	);
	
	
	
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
			@Parameter(description = "Optional book image", required = true) @RequestPart(value = "file", required = false) MultipartFile file
	);
    
    
	
	@Operation(
    		summary = "DELETE BOOKS",
    		description = "Delete a book"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "204", description = "Book deleted successfully"),
    		@ApiResponse(responseCode = "404", description = "Book not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<Void> eliminarLibro(
			@Parameter(description = "Book ID", required = true) @PathVariable Long id
	);
}
