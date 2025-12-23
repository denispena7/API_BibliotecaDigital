package es.library.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.library.springboot.DTOs.RatingDTO;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.mapper.RatingMapper;
import es.library.springboot.models.Rating;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.repositories.RatingRepository;
import es.library.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService 
{
	private final RatingRepository ratingRepo;
	private final BookRepository bookRepo;
	private final UserRepository userRepo;
	private final RatingMapper ratMapper;

	@Transactional(readOnly = true)
	public List<RatingDTO> obtenerRatingsPorLibro(String libro) 
	{
		bookRepo.findByTituloLibro(libro)
			.orElseThrow(() -> new EntityNotFoundException("Book not found"));
		
	    return ratMapper.toRatingDTOList(
	    		ratingRepo.findByLibroTituloLibro(libro)
	    		);
	}   		
	
	@Transactional(readOnly = true)
	public List<RatingDTO> obtenerRatingsPorUsuario(String usuario) 
	{
		userRepo.findByNombreUsuario(usuario)
		.orElseThrow(() -> new EntityNotFoundException("User not found"));
		
	    return ratMapper.toRatingDTOList(
	    		ratingRepo.findByUsuarioNombreUsuario(usuario)
	    		);
	}
	
	public Rating guardarRating(Rating rating) 
	{
		return ratingRepo.save(rating);
	}
}