package es.library.springboot.DTOs.requests;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(
		@NotBlank(message = "Category name is mandatory")
		String nombreCategoria
		) {}