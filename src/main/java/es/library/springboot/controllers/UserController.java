package es.library.springboot.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.UpdateEmailRequestDTO;
import es.library.springboot.DTOs.requests.UpdatePasswordRequestDTO;
import es.library.springboot.DTOs.requests.UserCreateAsAdminDTO;
import es.library.springboot.DTOs.requests.UserUpdateDTO;
import es.library.springboot.DTOs.responses.ApiResponse;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.DTOs.responses.UserSelfReadDTO;
import es.library.springboot.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController 
{
	private final UserService service;
	
	@PreAuthorize("hasAuthority('user:read:any')")
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<UserResponseDTO>>> listarUsuarios(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<UserResponseDTO> users = service.obtenerUsuarios(page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<UserResponseDTO>>builder()
				.data(users)
				.ok(true)
				.message("success")
				.build());
	}
	
	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponse<UserSelfReadDTO>> obtenerUsuarioPropio() 
	{
	    UserSelfReadDTO user = service.obtenerUsuarioActual();

	    return ResponseEntity.ok(
	        ApiResponse.<UserSelfReadDTO>builder()
	            .data(user)
	            .ok(true)
	            .message("success")
	            .build());
	}
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasAuthority('user:read:any')")
	public ResponseEntity<ApiResponse<UserResponseDTO>> obtenerUsuario(@PathVariable Long userId) 
	{
		UserResponseDTO user = service.obtenerUsuario(userId);

	    return ResponseEntity.ok(
	            ApiResponse.<UserResponseDTO>builder()
	                    .data(user)
	                    .ok(true)
	                    .message("success")
	                    .build()
	    );
	}

	@PreAuthorize("hasAuthority('user:role:assign:any')")
	@PatchMapping("/{id}/role")
	public ResponseEntity<ApiResponse<UserResponseDTO>> actualizarRolUsuario(
			@PathVariable Long id,
			@Valid @RequestBody UserCreateAsAdminDTO usuario)
	{
		UserResponseDTO user = service.actualizarRol(id, usuario);
		
		return ResponseEntity.ok(
				ApiResponse.<UserResponseDTO>builder()
					.data(user)
					.ok(true)
					.message("User role updated")
					.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('user:update:any') or
		    hasAuthority('user:update:self')
		""")
	@PatchMapping("/{id}/email")
	public ResponseEntity<ApiResponse<UserResponseDTO>> actualizarEmail(
			@PathVariable Long id, 
			@Valid @RequestBody UpdateEmailRequestDTO email)
	{
		UserResponseDTO user = service.actualizarEmail(id, email);
		
		return ResponseEntity.ok(
				ApiResponse.<UserResponseDTO>builder()
					.data(user)
					.ok(true)
					.message("User email updated")
					.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('user:update:any') or
		    hasAuthority('user:update:self')
		""")
	@PatchMapping(
			value = "/{id}/icon",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE
			)
	public ResponseEntity<ApiResponse<UserResponseDTO>> actualizarIcono(
			@PathVariable Long id, 
			@Valid @RequestPart(value = "file", required = false) MultipartFile icon)
	{
		UserResponseDTO user = service.actualizarIcono(id, icon);
		
		return ResponseEntity.ok(
				ApiResponse.<UserResponseDTO>builder()
					.data(user)
					.ok(true)
					.message("User icon updated")
					.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('user:update:any') or
		    hasAuthority('user:update:self')
		""")
	@PatchMapping("/{id}/password")
	public ResponseEntity<ApiResponse<UserResponseDTO>> actualizarClave(
			@PathVariable Long id, 
			@Valid @RequestBody UpdatePasswordRequestDTO newPassword)
	{
		UserResponseDTO user = service.actualizarPassword(id, newPassword);
		
		return ResponseEntity.ok(
				ApiResponse.<UserResponseDTO>builder()
					.data(user)
					.ok(true)
					.message("Password updated")
					.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('user:update:any') or
		    hasAuthority('user:update:self')
		""")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> actualizarUsuario(
			@PathVariable Long id, 
			@RequestBody UserUpdateDTO usuario)
	{
		UserResponseDTO user = service.actualizarInfoUsuario(id, usuario);
		
		return ResponseEntity.ok(
				ApiResponse.<UserResponseDTO>builder()
					.data(user)
					.ok(true)
					.message("User info updated")
					.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('user:delete:any') or
		    hasAuthority('user:delete:self')
		""")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> borrarUsuario(@PathVariable Long id)
	{
		service.eliminarUsuario(id);
	    return ResponseEntity.noContent().build();
	}
}