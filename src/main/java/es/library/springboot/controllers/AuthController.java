package es.library.springboot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.requests.LoginRequestDTO;
import es.library.springboot.DTOs.requests.UserRegisterDTO;
import es.library.springboot.DTOs.responses.ApiResponse;
import es.library.springboot.DTOs.responses.AuthResponseDTO;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.services.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController 
{
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) 
    {
    	return ResponseEntity.ok(
    			ApiResponse.<AuthResponseDTO>builder()
    			.data(authService.login(request))
    			.ok(true)
    			.message("You have loged in")
    			.build());
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody UserRegisterDTO dto) 
    {
        return ResponseEntity.ok(
        		ApiResponse.<UserResponseDTO>builder()
        		.data(authService.register(dto))
        		.ok(true)
        		.message("User registered")
        		.build());
    }
}