package es.library.springboot.DTOs.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequestDTO(
		@NotBlank(message = "Name is mandatory")
		String nombreAutor,
		@NotBlank(message = "Nationality is mandatory")
		String nacionalidadAutor,
		@NotBlank(message = "Birthdate is mandatory")
		String fechaNacimiento,
		String imagenAutor
		) {}