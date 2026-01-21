package es.library.springboot.DTOs.requests;

import jakarta.validation.constraints.NotBlank;

public record LoanUptRequestDTO(
		@NotBlank(message = "Loan state is mandatory")
		String estado
		) {}
