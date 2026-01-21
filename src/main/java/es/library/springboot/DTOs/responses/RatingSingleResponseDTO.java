package es.library.springboot.DTOs.responses;

public record RatingSingleResponseDTO(
		String emailUsuario,
		String tituloLibro,
		Double puntuacion
		) {}
