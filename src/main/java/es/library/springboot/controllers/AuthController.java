package es.library.springboot.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.requests.LoginRequestDTO;
import es.library.springboot.DTOs.requests.UserRegisterDTO;
import es.library.springboot.DTOs.responses.WraperResponse;
import es.library.springboot.controllers.api.AuthApi;
import es.library.springboot.controllers.base.BaseController;
import es.library.springboot.DTOs.responses.AuthResponseDTO;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController implements AuthApi
{
    private final AuthService authService;
    
    @Override
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WraperResponse<AuthResponseDTO>> login(
		@Valid @RequestBody LoginRequestDTO request) 
    {
    	AuthResponseDTO auth = authService.login(request);
    	
    	return ok(auth, "You have loged in");
    }
    
    
    @Override
    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WraperResponse<UserResponseDTO>> register(
    		@Valid @RequestBody UserRegisterDTO dto) 
    {
        UserResponseDTO user = authService.register(dto);

        return created(user, "User registered");
    }
}