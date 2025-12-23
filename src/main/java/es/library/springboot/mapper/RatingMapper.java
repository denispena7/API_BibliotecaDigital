package es.library.springboot.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.library.springboot.DTOs.RatingDTO;
import es.library.springboot.models.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper 
{
	@Mapping(source = "usuario.nombreUsuario", target = "nombreUsuario")
	@Mapping(source = "libro.tituloLibro", target = "tituloLibro")
    @Mapping(target = "puntuaciones", expression = "java(List.of(rating.getPuntuacion()))")
    RatingDTO toRatingDTO(Rating rating);
    
    List<RatingDTO> toRatingDTOList(List<Rating> ratings);

    // Este método sirve si ya tienes varias puntuaciones de un libro
//    default RatingDTO toAggregatedRatingDTO(List<Rating> ratings) {
//        return RatingDTO.builder()
//                .puntuaciones(ratings.stream()
//                        .map(Rating::getPuntuacion)
//                        .toList())
//                .build();
//    }
}