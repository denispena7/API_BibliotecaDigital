package es.library.springboot.DTOs.responses;

public record AvgRatingDTO(
		String tituloLibro,
		Double puntuacionMedia
		) {}
