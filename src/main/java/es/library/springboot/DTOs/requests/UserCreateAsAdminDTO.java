package es.library.springboot.DTOs.requests;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;

public record UserCreateAsAdminDTO(
	    @NotEmpty(message = "Provide at least one role")
	    List<String> roles
		) {}