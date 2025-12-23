package es.library.springboot.DTOs;

public record UserDTO(
		Long idUsuario,
		String nombreUsuario,
		String nombreRealUsuario,
		String apellidosUsuario,
		String direccionUsuario,
		String ciudadUsuario,
		String localidadUsuario,
		int cpUsuario,
		int telefonoUsuario,
		String emailUsuario,
		int tipoUsuario,
		String iconoUsuario
	) {}