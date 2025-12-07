package es.library.springboot.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.RatingDTO;
import es.library.springboot.models.Rating;
import es.library.springboot.services.RatingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingController 
{
    private final RatingService ratingService;

    @GetMapping("/libro/{titLibro}")
    public List<RatingDTO> getRatingsPorLibro(@PathVariable String titLibro) 
    {
    	return ratingService.obtenerRatingsPorLibro(titLibro);
    }
    
    @GetMapping("/usuario/{nomUsuario}")
    public List<RatingDTO> getRatingsPorUsuario(@PathVariable String nomUsuario) 
    {
    	return ratingService.obtenerRatingsPorUsuario(nomUsuario);
    }
    
    @PostMapping
    public Rating crearRating(@RequestBody Rating rating)
    {
        return ratingService.guardarRating(rating);
    }
}