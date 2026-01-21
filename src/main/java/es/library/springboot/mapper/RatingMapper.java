package es.library.springboot.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.library.springboot.DTOs.requests.RatingRequestDTO;
import es.library.springboot.DTOs.responses.RatingResponseDTO;
import es.library.springboot.DTOs.responses.RatingSingleResponseDTO;
import es.library.springboot.models.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper 
{
	@Mapping(source = "usuario.emailUsuario", target = "emailUsuario")
	@Mapping(source = "libro.tituloLibro", target = "tituloLibro")
    @Mapping(target = "puntuaciones", expression = "java(List.of(rating.getPuntuacion()))")
    RatingResponseDTO toRatingDTO(Rating rating);
	
	@Mapping(source = "usuario.emailUsuario", target = "emailUsuario")
	@Mapping(source = "libro.tituloLibro", target = "tituloLibro")
	@Mapping(source = "puntuacion", target = "puntuacion")
	RatingSingleResponseDTO toRatingResponse(Rating rating);
	
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "puntuacion", source = "puntuacion")
	Rating toRatingEntity(RatingRequestDTO rating);

    // Este método sirve si ya tienes varias puntuaciones de un libro
//    default RatingResponseDTO toAggregatedRatingDTO(List<Rating> ratings) {
//        return RatingResponseDTO.builder()
//                .puntuaciones(ratings.stream()
//                        .map(Rating::getPuntuacion)
//                        .toList())
//                .build();
//	}
}