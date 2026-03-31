package es.library.springboot.controllers.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.CategoryRequestDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.CategoryResponseDTO;
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

@Tag(name = "Categories", description = "CRUD operations for categories")
public interface CategoryApi 
{
    @Operation(
    		summary = "GET ALL CATEGORIES",
    		description = "Returns a paginated list of categories"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Categories returned successfully"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<PageResponse<CategoryResponseDTO>>> listarCategorias(
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);
	
    
    @Operation(
    		summary = "GET THE BOOKS OF A CATEGORY",
    		description = "Returns a paginated list of books of a category"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Books returned successfully"),
    		@ApiResponse(responseCode = "404", description = "Category not found"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<PageResponse<BookResponseDTO>>> obtenerLibros(
			@Parameter(description = "Category Name", required = true) @PathVariable String nombreCategoria,
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);
	

    @Operation(
    		summary = "CREATE CATEGORIES",
    		description = "Create a new category"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201", description = "Category created successfully"),
    		@ApiResponse(responseCode = "400", description = "Input type invalid"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<CategoryResponseDTO>> altaCategoria(
		    @Parameter(
		            description = "Category data (JSON)",
		            required = true,
		            content = @Content(
		                mediaType = MediaType.APPLICATION_JSON_VALUE,
		                schema = @Schema(implementation = CategoryRequestDTO.class)
		            )
		        )
			@RequestPart("data") @Valid CategoryRequestDTO cat,
			@Parameter(description = "Optional category image") @RequestPart(value = "file", required = false) MultipartFile file
	);
	

	
    @Operation(
    		summary = "UPDATE CATEGORIES",
    		description = "Update a category"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Category updated successfully"),
    		@ApiResponse(responseCode = "404", description = "Category not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<CategoryResponseDTO>> actualizarCategoria(
			@Parameter(description = "Category ID", required = true) @PathVariable Long id, 
		    @Parameter(
		            description = "Category data (JSON)",
		            required = true,
		            content = @Content(
		                mediaType = MediaType.APPLICATION_JSON_VALUE,
		                schema = @Schema(implementation = CategoryRequestDTO.class)
		            )
		        )
			@RequestPart("data") @Valid CategoryRequestDTO cat,
			@Parameter(description = "Optional Category Image", required = true)
			@RequestPart(value = "file", required = false) MultipartFile file
	);
	
	
	@Operation(
    		summary = "DELETE BOOKS",
    		description = "Delete a book"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "204", description = "Book deleted successfully"),
    		@ApiResponse(responseCode = "404", description = "Category not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<Void> eliminarCategoria(
			@Parameter(description = "Category ID", required = true) @PathVariable Long id
	);
}
