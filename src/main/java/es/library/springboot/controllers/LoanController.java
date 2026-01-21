package es.library.springboot.controllers;

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
import es.library.springboot.DTOs.responses.ApiResponse;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.LoanResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.services.LoanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loans")
public class LoanController 
{
	private final LoanService service;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/myLoans")
	public ResponseEntity<ApiResponse<PageResponse<LoanResponseDTO>>> prestamosUsuarioActual(
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<LoanResponseDTO> prestamos = service.obtenerPrestamosxUsuario(page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<LoanResponseDTO>>builder()
				.data(prestamos)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("hasAuthority('loan:read:any:user')")
	@GetMapping("/filters")
	public ResponseEntity<ApiResponse<PageResponse<LoanResponseDTO>>> filtrarPrestamos(
	        @RequestParam(required = false, defaultValue = "Pendiente") String estado,
	        @RequestParam(required = false) String fechaDesde,
	        @RequestParam(required = false) String fechaHasta,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<LoanResponseDTO> prestamos = service.obtenerPrestamosxFechayEstado(estado, fechaDesde, fechaHasta, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<LoanResponseDTO>>builder()
				.data(prestamos)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('loan:read:any') or
		    hasAuthority('loan:read:self')
		""")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<LoanResponseDTO>> detallesPrestamo(@PathVariable Long id)
	{
		LoanResponseDTO prestamo = service.obtenerPrestamo(id);
		
	    return ResponseEntity.ok(
	            ApiResponse.<LoanResponseDTO>builder()
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
	@GetMapping("/{idPrestamo}/books")
	public ResponseEntity<ApiResponse<PageResponse<BookResponseDTO>>> librosDelPrestamo(
			@PathVariable Long idPrestamo,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> libros = service.obtenerLibrosPrestamo(idPrestamo, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<BookResponseDTO>>builder()
				.data(libros)
				.ok(true)
				.message("success")
				.build());
	}

	@PreAuthorize("""
		    hasAuthority('loan:create:any:user') or
		    hasAuthority('loan:create:self')
		""")
	@PostMapping
	public ResponseEntity<ApiResponse<LoanResponseDTO>> altaPrestamo(
			@RequestBody LoanRequestDTO prestamo
	){
		LoanResponseDTO nuevoPrestamo = service.nuevoPrestamo(prestamo);
		
		return ResponseEntity.ok(
				ApiResponse.<LoanResponseDTO>builder()
				.data(nuevoPrestamo)
				.ok(true)
				.message("Loan created")
				.build());
	}
	
	@PreAuthorize("""
		    hasAuthority('loan:return:any:user') or
		    hasAuthority('loan:return:self')
		""")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<LoanResponseDTO>> actualizarPrestamo(
			@PathVariable Long id, 
			@RequestBody LoanUptRequestDTO prestamo
	){
		LoanResponseDTO prestamoAct = service.actualizarPrestamo(id, prestamo);
		
		return ResponseEntity.ok(
				ApiResponse.<LoanResponseDTO>builder()
				.data(prestamoAct)
				.ok(true)
				.message("Loan state updated")
				.build());
	}
}