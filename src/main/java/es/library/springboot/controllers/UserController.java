package es.library.springboot.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.UserDTO;
import es.library.springboot.models.User;
import es.library.springboot.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UserController 
{
	private final UserService service;
	
	@GetMapping("/consulta_usuarios")
	public List<UserDTO> listarUsuarios()
	{
		return service.obtenerUsuarios();
	}
	
	@GetMapping("/consulta_usuario/{nombre}")
	public ResponseEntity<UserDTO> obtenerUsuario(@PathVariable String nombre)
	{
	    return service.obtenerUsuario(nombre)
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.notFound().build());
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