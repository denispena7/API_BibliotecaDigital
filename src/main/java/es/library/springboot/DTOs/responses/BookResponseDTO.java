package es.library.springboot.DTOs.responses;

import java.util.List;

public record BookResponseDTO(
	    long idLibro,
	    String tituloLibro,
	    int anioPublicacion,
	    String sinopsisLibro,
		String estadoLibro,
		String portadaLibro,
		String nombreAutor,
		List<String> categorias
		) {}