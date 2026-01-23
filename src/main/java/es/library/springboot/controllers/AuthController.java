package es.library.springboot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.requests.LoginRequestDTO;
import es.library.springboot.DTOs.requests.UserRegisterDTO;
import es.library.springboot.DTOs.responses.WraperResponse;
import es.library.springboot.DTOs.responses.AuthResponseDTO;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User access to the API")
public class AuthController 
{
    private final AuthService authService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "LOGIN",
    		description = "Log in to get JWT and access to the endpoints"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Loged in successfully"),
    		@ApiResponse(responseCode = "404", description = "User not found"),
    		@ApiResponse(responseCode = "400", description = "Input type invalid"),
    		@ApiResponse(responseCode = "401", description = "Credentials incorrect")
    })
    public ResponseEntity<WraperResponse<AuthResponseDTO>> login(
		@Parameter(description = "Login data", required = true) @Valid @RequestBody LoginRequestDTO request) 
    {
    	return ResponseEntity.ok(
    			WraperResponse.<AuthResponseDTO>builder()
    			.data(authService.login(request))
    			.ok(true)
    			.message("You have loged in")
    			.build());
    }
    
    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "REGISTRATION",
    		description = "Register to create an user and access to the endpoints"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201", description = "User created"),
    		@ApiResponse(responseCode = "400", description = "Input type invalid")
    })
    public ResponseEntity<WraperResponse<UserResponseDTO>> register(
     @Parameter(description = "Registration data", required = true) @Valid @RequestBody UserRegisterDTO dto) 
    {
        UserResponseDTO user = authService.register(dto);

        WraperResponse<UserResponseDTO> response =
                WraperResponse.<UserResponseDTO>builder()
                        .data(user)
                        .ok(true)
                        .message("User registered")
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}