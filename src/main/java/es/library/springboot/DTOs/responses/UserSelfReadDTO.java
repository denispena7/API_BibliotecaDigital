package es.library.springboot.DTOs.responses;

public record UserSelfReadDTO(
		String nombreRealUsuario,
		String apellidosUsuario,
		String direccionUsuario,
		String ciudadUsuario,
		String localidadUsuario,
		int cpUsuario,
		int telefonoUsuario,
		String emailUsuario,
		String iconoUsuario
	) {}