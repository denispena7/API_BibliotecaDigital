package es.library.springboot.DTOs;

import java.time.LocalDate;

public record LoanDTO(
		Long idPrestamo,
		LocalDate fechaInicio,
		LocalDate fechaDevolucionEsperada,
		LocalDate fechaDevolucionReal,
		String estado,
		String nombreUsuario
		) {}