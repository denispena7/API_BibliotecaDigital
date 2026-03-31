package es.library.springboot.controllers.api;

import org.springframework.http.ResponseEntity;

import es.library.springboot.DTOs.requests.LoginRequestDTO;
import es.library.springboot.DTOs.requests.UserRegisterDTO;
import es.library.springboot.DTOs.responses.AuthResponseDTO;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.DTOs.responses.WraperResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "User access to the API")
public interface AuthApi 
{
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
    ResponseEntity<WraperResponse<AuthResponseDTO>> login(
            @Parameter(description = "Login data", required = true)
            @Valid @RequestBody LoginRequestDTO request
    );

    @Operation(
            summary = "REGISTRATION",
            description = "Register to create an user and access to the endpoints"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Input type invalid")
    })
    ResponseEntity<WraperResponse<UserResponseDTO>> register(
            @Parameter(description = "Registration data", required = true)
            @Valid @RequestBody UserRegisterDTO dto
    );
}
