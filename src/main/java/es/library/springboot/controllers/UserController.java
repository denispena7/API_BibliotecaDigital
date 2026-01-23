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
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.DTOs.responses.UserSelfReadDTO;
import es.library.springboot.DTOs.responses.WraperResponse;
import es.library.springboot.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User Administration")
public class UserController 
{
	private final UserService service;
	
	@PreAuthorize("hasAuthority('user:read:any')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "GET ALL USERS",
    		description = "Returns a paginated list of users"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Users returned successfully"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<PageResponse<UserResponseDTO>>> listarUsuarios(
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<UserResponseDTO> users = service.obtenerUsuarios(page, size);
		
		return ResponseEntity.ok(
				WraperResponse.<PageResponse<UserResponseDTO>>builder()
				.data(users)
				.ok(true)
				.message("success")
				.build());
	}
	
	@GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("isAuthenticated()")
    @Operation(
    		summary = "GET THE CURRENT USER",
    		description = "Returns the current user authenticated"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "User returned successfully"),
    		@ApiResponse(responseCode = "401", description = "No user authenticated"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<UserSelfReadDTO>> obtenerUsuarioPropio() 
	{
	    UserSelfReadDTO user = service.obtenerUsuarioActual();

	    return ResponseEntity.ok(
	        WraperResponse.<UserSelfReadDTO>builder()
	            .data(user)
	            .ok(true)
	            .message("success")
	            .build());
	}
	
	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('user:read:any')")
    @Operation(
    		summary = "GET A USER",
    		description = "Returns a user by their id"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "User returned successfully"),
    		@ApiResponse(responseCode = "404", description = "User not found"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<UserResponseDTO>> obtenerUsuario(
			@Parameter(description = "User ID", required = true) @PathVariable Long userId) 
	{
		UserResponseDTO user = service.obtenerUsuario(userId);

	    return ResponseEntity.ok(
	            WraperResponse.<UserResponseDTO>builder()
	                    .data(user)
	                    .ok(true)
	                    .message("success")
	                    .build()
	    );
	}

	@PreAuthorize("hasAuthority('user:role:assign:any')")
	@PatchMapping(value = "/{id}/role", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "UPDATE ROLE",
    		description = "Update user role"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "User role updated successfully"),
    		@ApiResponse(responseCode = "404", description = "User not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<UserResponseDTO>> actualizarRolUsuario(
			@Parameter(description = "User ID", required = true) @PathVariable Long id,
			@Parameter(description = "Role Data (JSON)", required = true) @Valid @RequestBody UserCreateAsAdminDTO usuario)
	{
		UserResponseDTO user = service.actualizarRol(id, usuario);
		
		return ResponseEntity.ok(
				WraperResponse.<UserResponseDTO>builder()
					.data(user)
					.ok(true)
					.message("User role updated")
					.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('user:update:any') or
		    hasAuthority('user:update:self')
		""")
	@PatchMapping(value = "/{id}/email", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "UPDATE EMAIL",
    		description = "Update user email"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Email updated successfully"),
    		@ApiResponse(responseCode = "404", description = "User not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<UserResponseDTO>> actualizarEmail(
			@Parameter(description = "User ID", required = true) @PathVariable Long id, 
			@Parameter(description = "Email Data (JSON)", required = true) @Valid @RequestBody UpdateEmailRequestDTO email)
	{
		UserResponseDTO user = service.actualizarEmail(id, email);
		
		return ResponseEntity.ok(
				WraperResponse.<UserResponseDTO>builder()
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
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
    @Operation(
    		summary = "UPDATE ICON",
    		description = "Update user icon"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Icon updated successfully"),
    		@ApiResponse(responseCode = "404", description = "User not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<UserResponseDTO>> actualizarIcono(
			@Parameter(description = "User ID", required = true) @PathVariable Long id, 
			@Parameter(description = "Optional User Image") @Valid @RequestPart(value = "file", required = false) MultipartFile icon)
	{
		UserResponseDTO user = service.actualizarIcono(id, icon);
		
		return ResponseEntity.ok(
				WraperResponse.<UserResponseDTO>builder()
					.data(user)
					.ok(true)
					.message("User icon updated")
					.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('user:update:any') or
		    hasAuthority('user:update:self')
		""")
	@PatchMapping(value = "/{id}/password", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "UPDATE PASSWORD",
    		description = "Update user password"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Password updated successfully"),
    		@ApiResponse(responseCode = "404", description = "User not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<UserResponseDTO>> actualizarClave(
			@Parameter(description = "User ID", required = true) @PathVariable Long id, 
			@Parameter(description = "Password Data (JSON)", required = true) @Valid @RequestBody UpdatePasswordRequestDTO newPassword)
	{
		UserResponseDTO user = service.actualizarPassword(id, newPassword);
		
		return ResponseEntity.ok(
				WraperResponse.<UserResponseDTO>builder()
					.data(user)
					.ok(true)
					.message("Password updated")
					.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('user:update:any') or
		    hasAuthority('user:update:self')
		""")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "UPDATE USER INFO",
    		description = "Update personal data"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "User updated successfully"),
    		@ApiResponse(responseCode = "404", description = "User not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<UserResponseDTO>> actualizarUsuario(
			@Parameter(description = "User ID", required = true) @PathVariable Long id, 
			@Parameter(description = "User Data (JSON)", required = true) @RequestBody UserUpdateDTO usuario)
	{
		UserResponseDTO user = service.actualizarInfoUsuario(id, usuario);
		
		return ResponseEntity.ok(
				WraperResponse.<UserResponseDTO>builder()
					.data(user)
					.ok(true)
					.message("User info updated")
					.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('user:delete:any') or
		    hasAuthority('user:delete:self')
		""")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
    		summary = "DELETE USERS",
    		description = "Delete an user"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "204", description = "Author deleted successfully"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<Void> borrarUsuario(@Parameter(description = "User ID", required = true) @PathVariable Long id)
	{
		service.eliminarUsuario(id);
	    return ResponseEntity.noContent().build();
	}
}