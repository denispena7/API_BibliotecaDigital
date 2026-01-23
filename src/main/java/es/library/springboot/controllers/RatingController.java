package es.library.springboot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.requests.RatingRequestDTO;
import es.library.springboot.DTOs.responses.AvgRatingDTO;
import es.library.springboot.DTOs.responses.RatingResponseDTO;
import es.library.springboot.DTOs.responses.RatingSingleResponseDTO;
import es.library.springboot.DTOs.responses.WraperResponse;
import es.library.springboot.services.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ratings")
@Tag(name = "Ratings", description = "CRUD operations for ratins")
public class RatingController 
{
    private final RatingService ratingService;
    
    @PreAuthorize("hasAuthority('avg:rating:read')")
    @GetMapping(value = "/avgScore/{titLibro}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    		@Parameter(description = "Book Title", required = true) @PathVariable String titLibro)
    {
    	AvgRatingDTO ratings = ratingService.obtenerPuntuacionMediaLibro(titLibro);
    	
    	return ResponseEntity.ok(
    			WraperResponse.<AvgRatingDTO>builder()
    			.data(ratings)
    			.ok(true)
    			.message("success")
    			.build());
    }

    // Lista de puntuaciones medias 
//    @PreAuthorize("hasAuthority('avg:rating:read')")
//    @GetMapping(value = "/avgScore/{titLibro}")
//    public ResponseEntity<ApiResponse<PageResponse<RatingDTO>>> getRatingsPorLibro(
//    		@PathVariable String titLibro,
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "10") int size
//    ) 
//    {
//    	PageResponse<RatingDTO> ratings = ratingService.obtenerPuntuacionMediaLibro(titLibro, page, size);
//    	
//    	return ResponseEntity.ok(
//    			ApiResponse.<PageResponse<RatingDTO>>builder()
//    			.data(ratings)
//    			.ok(true)
//    			.message("success")
//    			.build());
//    }
    
	@PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/score/{idLibro}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    		@Parameter(description = "Book ID", required = true) @PathVariable Long idLibro) 
    {
    	RatingResponseDTO ratings = ratingService.obtenerRatingPorLibro(idLibro);
    	
    	return ResponseEntity.ok(
    			WraperResponse.<RatingResponseDTO>builder()
    			.data(ratings)
    			.ok(true)
    			.message("success")
    			.build());
    }
    
    @PreAuthorize("hasAuthority('rating:create:self')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
    ){
    	RatingSingleResponseDTO nuevoRating = ratingService.nuevaPuntuacion(rating);

        WraperResponse<RatingSingleResponseDTO> response =
                WraperResponse.<RatingSingleResponseDTO>builder()
                        .data(nuevoRating)
                        .ok(true)
                        .message("Score saved")
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    
    @PreAuthorize("hasAuthority('rating:update:self')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
    ){
    	RatingSingleResponseDTO nuevoRating = ratingService.actPuntuacion(rating);
    	
    	return ResponseEntity.ok(
    			WraperResponse.<RatingSingleResponseDTO>builder()
    			.data(nuevoRating)
    			.ok(true)
    			.message("Score updated")
    			.build());
    }
}