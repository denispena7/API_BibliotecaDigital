package es.library.springboot.DTOs.responses;

public record CategoryResponseDTO(
	    long idCategoria,
	    String nombreCategoria,
	    String imagenCategoria
	) {}