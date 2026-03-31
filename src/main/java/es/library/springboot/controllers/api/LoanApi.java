package es.library.springboot.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import es.library.springboot.DTOs.requests.LoanRequestDTO;
import es.library.springboot.DTOs.requests.LoanUptRequestDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.LoanResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.DTOs.responses.WraperResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Loans", description = "CRUD operations for loans")
public interface LoanApi 
{
    @Operation(
    		summary = "GET THE CURRENT USER'S LOANS",
    		description = "Returns a paginated list of loans for the current user"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Loans returned successfully"),
    		@ApiResponse(responseCode = "401", description = "No user authenticated")
    })
	public ResponseEntity<WraperResponse<PageResponse<LoanResponseDTO>>> prestamosUsuarioActual(
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);
	
	
    @Operation(
    		summary = "GET LOANS WITH FILTERS",
    		description = "Returns a paginated list of loans with filters"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Loans returned successfully"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<PageResponse<LoanResponseDTO>>> filtrarPrestamos(
			@Parameter(description = "Loan State", required = true) @RequestParam(required = false, defaultValue = "Pendiente") String estado,
			@Parameter(description = "Start Date", required = true) @RequestParam(required = false) String fechaDesde,
			@Parameter(description = "Return Date", required = true) @RequestParam(required = false) String fechaHasta,
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);
	
	
	@Operation(
    		summary = "GET A LOAN",
    		description = "Returns a loan by its id"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Loan returned successfully"),
    		@ApiResponse(responseCode = "404", description = "Loan not found"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<LoanResponseDTO>> detallesPrestamo(
			@Parameter(description = "Loan ID", required = true) @PathVariable Long id
	);
	
	
	@Operation(
    		summary = "GET THE BOOKS OF A LOAN",
    		description = "Returns a paginated list of books of a loan"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Books returned successfully"),
    		@ApiResponse(responseCode = "404", description = "Loan not found"),
    		@ApiResponse(responseCode = "403", description = "Result not available for the current user")
    })
	public ResponseEntity<WraperResponse<PageResponse<BookResponseDTO>>> librosDelPrestamo(
			@Parameter(description = "Loan ID", required = true) @PathVariable Long idPrestamo,
			@Parameter(description = "Page", required = true) @RequestParam(defaultValue = "0") int page,
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size
	);

	@Operation(
    		summary = "CREATE LOANS",
    		description = "Create a new loan"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "201", description = "Loan created successfully"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<LoanResponseDTO>> altaPrestamo(
			@Parameter(description = "Loan Data (JSON)", required = true)
			@RequestBody LoanRequestDTO prestamo
	);
	
	@Operation(
    		summary = "UPDATE LOANS",
    		description = "Update a loan"
    )
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Loan created successfully"),
    		@ApiResponse(responseCode = "404", description = "Loan not found"),
    		@ApiResponse(responseCode = "403", description = "Action not available for the current user")
    })
	public ResponseEntity<WraperResponse<LoanResponseDTO>> actualizarPrestamo(
			@Parameter(description = "Loan ID", required = true) @PathVariable Long id, 
			@Parameter(description = "Loan Data (JSON)", required = true) @RequestBody LoanUptRequestDTO prestamo
	);
}
