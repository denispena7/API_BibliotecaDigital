package es.library.springboot.controllers;

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
import es.library.springboot.controllers.api.RatingApi;
import es.library.springboot.controllers.base.BaseController;
import es.library.springboot.services.RatingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ratings")
public class RatingController extends BaseController implements RatingApi
{
    private final RatingService ratingService;
    
    @Override
    @PreAuthorize("hasAuthority('avg:rating:read')")
    @GetMapping(value = "/avgScore/{titLibro}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WraperResponse<AvgRatingDTO>> puntuacionMedia(@PathVariable String titLibro)
    {
    	AvgRatingDTO ratings = ratingService.obtenerPuntuacionMediaLibro(titLibro);
    	
    	return ok(ratings, "success");
    }
    
    
    @Override
	@PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/score/{idLibro}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WraperResponse<RatingResponseDTO>> getRatingPorLibro(
    		@PathVariable Long idLibro) 
    {
    	RatingResponseDTO ratings = ratingService.obtenerRatingPorLibro(idLibro);
    	
    	return ok(ratings, "success");
    }
    
    
    @Override
    @PreAuthorize("hasAuthority('rating:create:self')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WraperResponse<RatingSingleResponseDTO>> crearRating(
    		@RequestBody RatingRequestDTO rating)
    {
    	RatingSingleResponseDTO nuevoRating = ratingService.nuevaPuntuacion(rating);

        return created(nuevoRating, "Score saved");
    }
    
   
    @Override
    @PreAuthorize("hasAuthority('rating:update:self')")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WraperResponse<RatingSingleResponseDTO>> actualizarRating(
    		@RequestBody RatingRequestDTO rating)
    {
    	RatingSingleResponseDTO nuevoRating = ratingService.actPuntuacion(rating);
    	
    	return ok(nuevoRating, "Score updated");
    }
}