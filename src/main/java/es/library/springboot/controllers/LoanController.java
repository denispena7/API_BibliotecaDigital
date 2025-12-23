package es.library.springboot.controllers;

import java.util.List;
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
import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.LoanDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.models.Loan;
import es.library.springboot.services.LoanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loans")
public class LoanController 
{
	private final LoanService service;
	
	@GetMapping(headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<PageResponse<LoanDTO>>> listarPrestamos(
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<LoanDTO> prestamos = service.obtenerPrestamos(page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<LoanDTO>>builder()
				.data(prestamos)
				.ok(true)
				.message("success")
				.build());
	}
	
	@GetMapping(value = "/byUser", headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<PageResponse<LoanDTO>>> listarPrestamosxUsuario(
			@RequestParam String nombreUsuario,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<LoanDTO> prestamos = service.obtenerPrestamosxUsuario(nombreUsuario, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<LoanDTO>>builder()
				.data(prestamos)
				.ok(true)
				.message("success")
				.build());
	}
	
	@GetMapping(value = "/filters", headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<PageResponse<LoanDTO>>> buscarPrestamos(
	        @RequestParam(required = false, defaultValue = "Pendiente") String estado,
	        @RequestParam(required = false) String fechaDesde,
	        @RequestParam(required = false) String fechaHasta,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
		PageResponse<LoanDTO> prestamos = service.obtenerPrestamosxFechayEstado(estado, fechaDesde, fechaHasta, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<LoanDTO>>builder()
				.data(prestamos)
				.ok(true)
				.message("success")
				.build());
	}
	
	@GetMapping(value = "/{id}", headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<LoanDTO>> obtenerPrestamoRealizados(@PathVariable Long id)
	{
		LoanDTO prestamo = service.obtenerPrestamo(id);
		
	    return ResponseEntity.ok(
	            ApiResponse.<LoanDTO>builder()
	                    .data(prestamo)
	                    .ok(true)
	                    .message("success")
	                    .build()
	    );
	}
	
	@GetMapping(value = "/{idPrestamo}/books", headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<List<BookDTO>>> obtenerLibrosxPrestamo(@PathVariable Long idPrestamo)
	{
		List<BookDTO> libros = service.obtenerLibrosPrestamo(idPrestamo);
		
		return ResponseEntity.ok(
				ApiResponse.<List<BookDTO>>builder()
				.data(libros)
				.ok(true)
				.message("success")
				.build());
	}

	@PostMapping("/alta_prestamo")
	public Loan altaPrestamo(@RequestBody Loan prestamo)
	{
		return service.guardarPrestamo(prestamo);
	}
	
	@PutMapping("act_prestamo/{id}")
	public Loan actualizarPrestamo(@PathVariable Long id, @RequestBody Loan prestamo)
	{
		return service.actualizar(id, prestamo);
	}
}