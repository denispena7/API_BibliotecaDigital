package es.library.springboot.controllers.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.AuthorRequestDTO;
import es.library.springboot.DTOs.responses.AuthorResponseDTO;
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

@Tag(name = "Authors", description = "CRUD operations for authors")
public interface AuthorApi 
{
    @Operation(
    		summary = "GET ALL AUTHORS",
    		description = "Returns a paginated list of authors"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Authors returned successfully"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<PageResponse<AuthorResponseDTO>>> listarAutores(
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);

    
    @Operation(
    		summary = "GET THE BOOKS AN AUTHOR WROTE",
    		description = "Returns a paginated list of books written by the author specified"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Books returned successfully"),
    		@ApiResponse(responseCode = "404", description = "Author not found"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<PageResponse<BookResponseDTO>>> obtenerLibros(
			@Parameter(description = "Author name", required = true) @PathVariable String nombreAutor,
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);
	
	

    @Operation(
    		summary = "CREATE AUTHORS",
    		description = "Create a new author"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201", description = "Author created successfully"),
    		@ApiResponse(responseCode = "400", description = "Input type invalid"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<AuthorResponseDTO>> altaAutor(
		    @Parameter(
		            description = "Author data (JSON)",
		            required = true,
		            content = @Content(
		                mediaType = MediaType.APPLICATION_JSON_VALUE,
		                schema = @Schema(implementation = AuthorRequestDTO.class)
		            )
		        )
			@Valid @RequestPart("data") AuthorRequestDTO autor,
			@Parameter(description = "Optional author image")
			@RequestPart(value = "file", required = false) MultipartFile file
	);
    
    
    @Operation(
    		summary = "UPDATE AUTHORS",
    		description = "Update an author"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Author updated successfully"),
    		@ApiResponse(responseCode = "400", description = "Input type invalid"),
    		@ApiResponse(responseCode = "404", description = "Author not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<AuthorResponseDTO>> actualizarAutor(
			@Parameter(description = "Author ID", required = true) @PathVariable Long id,
		    @Parameter(
		            description = "Author data (JSON)",
		            required = true,
		            content = @Content(
		                mediaType = MediaType.APPLICATION_JSON_VALUE,
		                schema = @Schema(implementation = AuthorRequestDTO.class)
		            )
		        )
			@Valid @RequestPart("data") AuthorRequestDTO autor,
			@Parameter(description = "Optional author image") @RequestPart(value = "file", required = false) MultipartFile file
	);
	
	
    @Operation(
    		summary = "DELETE AUTHORS",
    		description = "Delete an author"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "204", description = "Author deleted successfully"),
    		@ApiResponse(responseCode = "404", description = "Author not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<Void> bajaAutor(
			@Parameter(description = "Author ID", required = true) @PathVariable Long id
	);
}