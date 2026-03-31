package es.library.springboot.DTOs.requests;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO(
		@NotBlank(message = "Name is mandatory")
		String nombreRealUsuario,
		
		@NotBlank(message = "Lastname is mandatory")
		String apellidosUsuario,
		
		String direccionUsuario,
		
		String ciudadUsuario,
		
		String localidadUsuario,
		
		int cpUsuario,
		
		int telefonoUsuario
		) {}