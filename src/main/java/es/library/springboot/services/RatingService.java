package es.library.springboot.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.library.springboot.DTOs.requests.RatingRequestDTO;
import es.library.springboot.DTOs.responses.AvgRatingDTO;
import es.library.springboot.DTOs.responses.RatingResponseDTO;
import es.library.springboot.DTOs.responses.RatingSingleResponseDTO;
import es.library.springboot.config.SecurityUtils;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.mapper.RatingMapper;
import es.library.springboot.models.Book;
import es.library.springboot.models.Rating;
import es.library.springboot.models.RatingId;
import es.library.springboot.models.User;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService 
{
	private final RatingRepository ratingRepo;
	private final BookRepository bookRepo;
	private final RatingMapper ratMapper;
	
	Pageable pageable;

	@Transactional(readOnly = true)
	public AvgRatingDTO obtenerPuntuacionMediaLibro(String libro) 
	{
	    Book book = bookRepo.findByTituloLibro(libro)
	            .orElseThrow(() -> new EntityNotFoundException("Book not found"));

	        Double avg = ratingRepo.findAvgRatingByLibro(libro);

	        return new AvgRatingDTO(
	            book.getTituloLibro(),
	            avg != null ? avg : 0.0
	        );
	} 
	
	@Transactional(readOnly = true)
	public RatingResponseDTO obtenerRatingPorLibro(Long idLibro) 
	{	
		User user = SecurityUtils.getCurrentUser();
		
		return ratMapper.toRatingDTO(
				ratingRepo.findByUsuarioIdUsuarioAndLibroIdLibro(user.getIdUsuario(), idLibro)
				.orElseThrow(() -> new EntityNotFoundException("Score not found"))
				);
	}

	@Transactional
	public RatingSingleResponseDTO nuevaPuntuacion(RatingRequestDTO rating) 
	{
		User user = SecurityUtils.getCurrentUser();
		
		Book libro = bookRepo.findById(rating.idLibro())
				.orElseThrow(() -> new EntityNotFoundException("Book not found"));
		
	    RatingId ratingId = new RatingId(
	            user.getIdUsuario(),
	            libro.getIdLibro()
	    );
		
		Rating score = ratMapper.toRatingEntity(rating);
		
		score.setId(ratingId);
		score.setUsuario(user);
		score.setLibro(libro);
		
		Rating saved = ratingRepo.save(score);
		
		return ratMapper.toRatingResponse(saved);
	}

	@Transactional
	public RatingSingleResponseDTO actPuntuacion(RatingRequestDTO rating)
	{
		User user = SecurityUtils.getCurrentUser();
		
		Rating score = ratingRepo.findByUsuarioIdUsuarioAndLibroIdLibro(
						user.getIdUsuario(), rating.idLibro())
						.orElseThrow(() -> new EntityNotFoundException("Score not found"));
		
		score.setPuntuacion(rating.puntuacion());
		
		Rating updated = ratingRepo.save(score);
		
		return ratMapper.toRatingResponse(updated);
	}
}