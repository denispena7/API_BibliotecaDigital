package es.library.springboot.DTOs.responses;

import java.time.LocalDate;

public record LoanResponseDTO(
		Long idPrestamo,
		LocalDate fechaInicio,
		LocalDate fechaDevolucionEsperada,
		LocalDate fechaDevolucionReal,
		String estado,
		String emailUsuario
		) {}