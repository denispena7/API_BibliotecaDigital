package es.library.springboot.DTOs;

import java.time.LocalDate;

public record AuthorDTO(
		Long idAutor,
		String nombreAutor,
		String nacionalidadAutor,
		LocalDate fechaNacimiento,
		String imagenAutor
	) {}