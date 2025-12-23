package es.library.springboot.DTOs;

public record CategoryDTO(
	    long idCategoria,
	    String nombreCategoria,
	    String imagenCategoria
	) {}