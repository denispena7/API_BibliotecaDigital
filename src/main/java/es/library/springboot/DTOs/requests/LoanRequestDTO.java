package es.library.springboot.DTOs.requests;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoanRequestDTO(	
	    @JsonFormat(pattern = "dd/MM/yyyy")
		@NotBlank(message = "Initial date is mandatory")
	    LocalDate fechaInicio,

	    @JsonFormat(pattern = "dd/MM/yyyy")
		@NotBlank(message = "Expected return date is mandatory")
	    LocalDate fechaDevolucionEsperada,

	    @JsonFormat(pattern = "dd/MM/yyyy")
	    LocalDate fechaDevolucionReal,
		
		@NotBlank(message = "Loan state is mandatory")
		String estado,
		
		@NotNull(message = "User id is mandatory")
		Long idUsuario,
		
		@NotNull(message = "Loan books are mandatory")
		List<Integer> libros
		) {}
