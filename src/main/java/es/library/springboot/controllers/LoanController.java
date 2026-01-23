package es.library.springboot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.requests.LoanRequestDTO;
import es.library.springboot.DTOs.requests.LoanUptRequestDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.LoanResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.DTOs.responses.WraperResponse;
import es.library.springboot.services.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
@Tag(name = "Loans", description = "CRUD operations for loans")
public class LoanController 
{
	private final LoanService service;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/myLoans", produces = MediaType.APPLICATION_JSON_VALUE)
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
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<LoanResponseDTO> prestamos = service.obtenerPrestamosxUsuario(page, size);
		
		return ResponseEntity.ok(
				WraperResponse.<PageResponse<LoanResponseDTO>>builder()
				.data(prestamos)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("hasAuthority('loan:read:any:user')")
	@GetMapping(value = "/filters", produces = MediaType.APPLICATION_JSON_VALUE)
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
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<LoanResponseDTO> prestamos = service.obtenerPrestamosxFechayEstado(estado, fechaDesde, fechaHasta, page, size);
		
		return ResponseEntity.ok(
				WraperResponse.<PageResponse<LoanResponseDTO>>builder()
				.data(prestamos)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('loan:read:any') or
		    hasAuthority('loan:read:self')
		""")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
			@Parameter(description = "Loan ID", required = true) @PathVariable Long id)
	{
		LoanResponseDTO prestamo = service.obtenerPrestamo(id);
		
	    return ResponseEntity.ok(
	            WraperResponse.<LoanResponseDTO>builder()
	                    .data(prestamo)
	                    .ok(true)
	                    .message("success")
	                    .build()
	    );
	}
	
	@PreAuthorize("""
		    hasAuthority('loan:read:any') or
		    hasAuthority('loan:read:self')
		""")
	@GetMapping(value = "/{idPrestamo}/books", produces = MediaType.APPLICATION_JSON_VALUE)
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
			@Parameter(description = "Size", required = true) @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> libros = service.obtenerLibrosPrestamo(idPrestamo, page, size);
		
		return ResponseEntity.ok(
				WraperResponse.<PageResponse<BookResponseDTO>>builder()
				.data(libros)
				.ok(true)
				.message("success")
				.build());
	}

	@PreAuthorize("""
		    hasAuthority('loan:create:any:user') or
		    hasAuthority('loan:create:self')
		""")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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
	){
		LoanResponseDTO nuevoPrestamo = service.nuevoPrestamo(prestamo);

        WraperResponse<LoanResponseDTO> response =
                WraperResponse.<LoanResponseDTO>builder()
                        .data(nuevoPrestamo)
                        .ok(true)
                        .message("Loan created")
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
	}
	
	@PreAuthorize("""
		    hasAuthority('loan:return:any:user') or
		    hasAuthority('loan:return:self')
		""")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
	){
		LoanResponseDTO prestamoAct = service.actualizarPrestamo(id, prestamo);
		
		return ResponseEntity.ok(
				WraperResponse.<LoanResponseDTO>builder()
				.data(prestamoAct)
				.ok(true)
				.message("Loan state updated")
				.build());
	}
}