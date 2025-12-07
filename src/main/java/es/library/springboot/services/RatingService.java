package es.library.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import es.library.springboot.DTOs.RatingDTO;
import es.library.springboot.mapper.RatingMapper;
import es.library.springboot.models.Rating;
import es.library.springboot.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService 
{
	private final RatingRepository ratingRepo;
	private final RatingMapper ratMapper;

	public List<RatingDTO> obtenerRatingsPorLibro(String libro) 
	{
	    return ratMapper.toRatingDTOList(
	    		ratingRepo.findByLibroTituloLibro(libro)
	    		);
	}   			
	public List<RatingDTO> obtenerRatingsPorUsuario(String usuario) 
	{
	    return ratMapper.toRatingDTOList(
	    		ratingRepo.findByUsuario_NombreUsuario(usuario)
	    		);
	}
	
	public Rating guardarRating(Rating rating) 
	{
		return ratingRepo.save(rating);
	}
}