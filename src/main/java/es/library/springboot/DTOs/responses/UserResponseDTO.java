package es.library.springboot.DTOs.responses;

public record UserResponseDTO(
		 Long idUsuario,
		 String nombreRealUsuario,
		 String apellidosUsuario,
		 String emailUsuario
		) {}
