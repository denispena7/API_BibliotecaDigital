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
public class LoanDTO 
{
	private Long idPrestamo;
	private LocalDate fechaInicio;
	private LocalDate fechaDevolucionEsperada;
	private LocalDate fechaDevolucionReal;
	private String estado;
	private String nombreUsuario;
}