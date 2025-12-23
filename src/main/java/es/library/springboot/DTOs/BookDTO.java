package es.library.springboot.DTOs;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record BookDTO(
	    long idLibro,
	    @NotNull String tituloLibro,
	    int anioPublicacion,
	    String sinopsisLibro,
		String estadoLibro,
		String portadaLibro,
		String nombreAutor,
		List<String> categorias
		) {}