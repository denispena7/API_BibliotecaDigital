package es.library.springboot.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.ApiResponse;
import es.library.springboot.DTOs.RatingDTO;
import es.library.springboot.models.Rating;
import es.library.springboot.services.RatingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ratings")
public class RatingController 
{
    private final RatingService ratingService;

    @GetMapping(value = "/byBook/{titLibro}", headers = "Api-Version=1")
    public ResponseEntity<ApiResponse<List<RatingDTO>>> getRatingsPorLibro(@PathVariable String titLibro) 
    {
    	List<RatingDTO> ratings = ratingService.obtenerRatingsPorLibro(titLibro);
    	
    	return ResponseEntity.ok(
    			ApiResponse.<List<RatingDTO>>builder()
    			.data(ratings)
    			.ok(true)
    			.message("success")
    			.build());
    }
    
    @GetMapping(value = "/byUser/{nomUsuario}", headers = "Api-Version=1")
    public ResponseEntity<ApiResponse<List<RatingDTO>>> getRatingsPorUsuario(@PathVariable String nomUsuario) 
    {
    	List<RatingDTO> ratings = ratingService.obtenerRatingsPorUsuario(nomUsuario);
    	
    	return ResponseEntity.ok(
    			ApiResponse.<List<RatingDTO>>builder()
    			.data(ratings)
    			.ok(true)
    			.message("success")
    			.build());
    }
    
    @PostMapping
    public Rating crearRating(@RequestBody Rating rating)
    {
        return ratingService.guardarRating(rating);
    }
}