package es.library.springboot.controllers;

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
import es.library.springboot.DTOs.responses.ApiResponse;
import es.library.springboot.DTOs.responses.AvgRatingDTO;
import es.library.springboot.DTOs.responses.RatingResponseDTO;
import es.library.springboot.DTOs.responses.RatingSingleResponseDTO;
import es.library.springboot.services.RatingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ratings")
public class RatingController 
{
    private final RatingService ratingService;
    
    @PreAuthorize("hasAuthority('avg:rating:read')")
    @GetMapping(value = "/avgScore/{titLibro}")
    public ResponseEntity<ApiResponse<AvgRatingDTO>> puntuacionMedia(@PathVariable String titLibro)
    {
    	AvgRatingDTO ratings = ratingService.obtenerPuntuacionMediaLibro(titLibro);
    	
    	return ResponseEntity.ok(
    			ApiResponse.<AvgRatingDTO>builder()
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
    @GetMapping("/score/{idLibro}")
    public ResponseEntity<ApiResponse<RatingResponseDTO>> getRatingPorLibro(@PathVariable Long idLibro) 
    {
    	RatingResponseDTO ratings = ratingService.obtenerRatingPorLibro(idLibro);
    	
    	return ResponseEntity.ok(
    			ApiResponse.<RatingResponseDTO>builder()
    			.data(ratings)
    			.ok(true)
    			.message("success")
    			.build());
    }
    
    @PreAuthorize("hasAuthority('rating:create:self')")
    @PostMapping
    public ResponseEntity<ApiResponse<RatingSingleResponseDTO>> crearRating(
    		@RequestBody RatingRequestDTO rating
    ){
    	RatingSingleResponseDTO nuevoRating = ratingService.nuevaPuntuacion(rating);
    	
    	return ResponseEntity.ok(
    			ApiResponse.<RatingSingleResponseDTO>builder()
    			.data(nuevoRating)
    			.ok(true)
    			.message("Score saved")
    			.build());
    }
    
    @PreAuthorize("hasAuthority('rating:update:self')")
    @PutMapping
    public ResponseEntity<ApiResponse<RatingSingleResponseDTO>> actualizarRating(
    		@RequestBody RatingRequestDTO rating
    ){
    	RatingSingleResponseDTO nuevoRating = ratingService.actPuntuacion(rating);
    	
    	return ResponseEntity.ok(
    			ApiResponse.<RatingSingleResponseDTO>builder()
    			.data(nuevoRating)
    			.ok(true)
    			.message("Score updated")
    			.build());
    }
}