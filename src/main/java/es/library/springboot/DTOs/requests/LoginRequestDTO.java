package es.library.springboot.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO
{
	@NotBlank(message = "Email is mandatory")
    private String email;
	@NotBlank(message = "Password is mandatory")
    private String password;
}