package es.library.springboot.DTOs.responses;

import java.util.List;

public record RatingResponseDTO(
		String emailUsuario,
		String tituloLibro,
		List<Double> puntuaciones
		) {}