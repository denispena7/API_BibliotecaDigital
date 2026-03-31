package es.library.springboot.DTOs.requests;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequestDTO(
		@NotBlank(message = "Book title is mandatory")
	    String tituloLibro,
	    @NotNull(message = "Release year is mandatory")
	    int anioPublicacion,
	    @NotBlank(message = "Description is mandatory")
	    String sinopsisLibro,
	    @NotBlank(message = "Book state is mandatory")
		String estadoLibro,
		@NotBlank(message = "Book author is mandatory")
		String nombreAutor,
		@NotEmpty(message = "Book categories are mandatory")
		List<String> categorias
		) {}