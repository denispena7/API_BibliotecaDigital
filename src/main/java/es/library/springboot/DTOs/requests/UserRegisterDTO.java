package es.library.springboot.DTOs.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
	@NotBlank(message = "Name is mandatory")
    String nombreRealUsuario,
    
    @NotBlank(message = "Lastname is mandatory")
    String apellidosUsuario,
    
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email format is not valid")
    String emailUsuario,
    
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$",
        message = "Password must contain uppercase, lowercase, number and symbol"
    )
    String password
) {}