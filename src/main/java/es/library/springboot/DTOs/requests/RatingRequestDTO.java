package es.library.springboot.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RatingRequestDTO(
		@NotBlank(message = "Book id is mandatory")
		Long idLibro,
		@NotNull(message = "Book score is mandatory")
		Double puntuacion
		) {}
