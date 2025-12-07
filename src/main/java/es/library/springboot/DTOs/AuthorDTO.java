package es.library.springboot.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO 
{
	private Long idAutor;
	private String nombreAutor;
	private String nacionalidadAutor;
	private LocalDate fechaNacimiento;
	private String imagenAutor;
}