package es.library.springboot.DTOs;

import java.util.List;

public record RatingDTO(
		String nombreUsuario,
		String tituloLibro,
		List<Double> puntuaciones
		) {}