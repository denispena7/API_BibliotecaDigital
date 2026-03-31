package es.library.springboot.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import es.library.springboot.DTOs.requests.RatingRequestDTO;
import es.library.springboot.DTOs.responses.AvgRatingDTO;
import es.library.springboot.DTOs.responses.RatingResponseDTO;
import es.library.springboot.DTOs.responses.RatingSingleResponseDTO;
import es.library.springboot.DTOs.responses.WraperResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ratings", description = "CRUD operations for ratins")
public interface RatingApi 
{
    @Operation(
    		summary = "GET THE AVERAGE SCORE FOR A BOOK",
    		description = "Returns the average score for a book voted for users"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Average rating returned successfully"),
    		@ApiResponse(responseCode = "404", description = "Book not found"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
    public ResponseEntity<WraperResponse<AvgRatingDTO>> puntuacionMedia(
    		@Parameter(description = "Book Title", required = true) @PathVariable String titLibro
    );
    
	@Operation(
    		summary = "GET A USER'S BOOK SCORE",
    		description = "Returns the score for a book voted for a user"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Score returned successfully"),
    		@ApiResponse(responseCode = "404", description = "Book not found"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
    public ResponseEntity<WraperResponse<RatingResponseDTO>> getRatingPorLibro(
    		@Parameter(description = "Book ID", required = true) @PathVariable Long idLibro
    ); 
    
    @Operation(
    		summary = "CREATE RATINGS",
    		description = "Create a new rating for a book"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201", description = "Rating created successfully"),
    		@ApiResponse(responseCode = "404", description = "Book not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
    public ResponseEntity<WraperResponse<RatingSingleResponseDTO>> crearRating(
    		@Parameter(description = "Rating Data (JSON)", required = true) @RequestBody RatingRequestDTO rating
    );
    
    @Operation(
    		summary = "UPDATE RATINGS",
    		description = "Update a score"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Rating updated successfully"),
    		@ApiResponse(responseCode = "404", description = "Rating not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
    public ResponseEntity<WraperResponse<RatingSingleResponseDTO>> actualizarRating(
    		@Parameter(description = "Rating Data (JSON)", required = true) @RequestBody RatingRequestDTO rating
    );
}
