package es.library.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO 
{
	private Long idUsuario;
	private String nombreUsuario;
	private String nombreRealUsuario;
	private String apellidosUsuario;
	private String direccionUsuario;
	private String ciudadUsuario;
	private String localidadUsuario;
	private int cpUsuario;
	private int telefonoUsuario;
	private String emailUsuario;
	private int tipoUsuario;
	private String iconoUsuario;
}