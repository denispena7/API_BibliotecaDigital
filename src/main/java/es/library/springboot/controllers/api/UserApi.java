package es.library.springboot.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.UpdateEmailRequestDTO;
import es.library.springboot.DTOs.requests.UpdatePasswordRequestDTO;
import es.library.springboot.DTOs.requests.UserCreateAsAdminDTO;
import es.library.springboot.DTOs.requests.UserUpdateDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.DTOs.responses.UserSelfReadDTO;
import es.library.springboot.DTOs.responses.WraperResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Users", description = "User Administration")
public interface UserApi 
{
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
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);
	
	
	
	@Operation(
    		summary = "GET THE CURRENT USER",
    		description = "Returns the current user authenticated"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "User returned successfully"),
    		@ApiResponse(responseCode = "401", description = "No user authenticated"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<UserSelfReadDTO>> obtenerUsuarioPropio();
	
	
	
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
			@Parameter(description = "User ID", required = true) @PathVariable Long userId
	);

	
	
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
			@Parameter(description = "Role Data (JSON)", required = true) @Valid @RequestBody UserCreateAsAdminDTO usuario
	);
	
	
	
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
			@Parameter(description = "Email Data (JSON)", required = true) @Valid @RequestBody UpdateEmailRequestDTO email
	);
	

    
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
			@Parameter(description = "Optional User Image") @Valid @RequestPart(value = "file", required = false) MultipartFile icon
	);
	
	
	
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
			@Parameter(description = "Password Data (JSON)", required = true) @Valid @RequestBody UpdatePasswordRequestDTO newPassword
	);
	

   
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
			@Parameter(description = "User Data (JSON)", required = true) @RequestBody UserUpdateDTO usuario
	);
	
	
	
	@Operation(
    		summary = "DELETE USERS",
    		description = "Delete an user"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "204", description = "Author deleted successfully"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<Void> borrarUsuario(
			@Parameter(description = "User ID", required = true) @PathVariable Long id
	);
}
