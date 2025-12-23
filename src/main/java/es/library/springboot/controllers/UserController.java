package es.library.springboot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.ApiResponse;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.DTOs.UserDTO;
import es.library.springboot.models.User;
import es.library.springboot.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController 
{
	private final UserService service;
	
	@GetMapping(headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<PageResponse<UserDTO>>> listarUsuarios(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<UserDTO> users = service.obtenerUsuarios(page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<UserDTO>>builder()
				.data(users)
				.ok(true)
				.message("success")
				.build());
	}
	
	@GetMapping(value = "/{id}", headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<UserDTO>> obtenerUsuario(@PathVariable Long id) 
	{
	    UserDTO user = service.obtenerUsuario(id);

	    return ResponseEntity.ok(
	            ApiResponse.<UserDTO>builder()
	                    .data(user)
	                    .ok(true)
	                    .message("success")
	                    .build()
	    );
	}
	
	@PostMapping("/alta_usuario")
	public User altaUsuario(@RequestBody User usuario)
	{
		return service.guardarUsuario(usuario);
	}
	
	@PutMapping("act_usuario/{id}")
	public User actualizarUsuario(@PathVariable Long id, @RequestBody User usuario)
	{
		return service.actualizar(id, usuario);
	}
}