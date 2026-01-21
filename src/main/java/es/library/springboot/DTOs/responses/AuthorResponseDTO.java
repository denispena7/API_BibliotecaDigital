package es.library.springboot.DTOs.responses;

import java.time.LocalDate;

public record AuthorResponseDTO(
		Long idAutor,
		String nombreAutor,
		String nacionalidadAutor,
		LocalDate fechaNacimiento,
		String imagenAutor
	) {}