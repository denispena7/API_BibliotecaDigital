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

import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.LoanDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.models.Loan;
import es.library.springboot.services.LoanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prestamos")
public class LoanController 
{
	private final LoanService service;
	
	@GetMapping("/consulta_prestamos_usuarios")
	public PageResponse<LoanDTO> listarPrestamos(
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
		return service.obtenerPrestamos(page, size);
	}
	
	@GetMapping("/consulta_prestamos_usuario")
	public PageResponse<LoanDTO> listarPrestamosxUsuario(
			@RequestParam String nombreUsuario,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
		return service.obtenerPrestamosxUsuario(nombreUsuario, page, size);
	}
	
	@GetMapping("/prestamosFiltros")
	public PageResponse<LoanDTO> buscarPrestamos(
	        @RequestParam(defaultValue = "Pendiente") String estado,
	        @RequestParam(required = false) String fechaDesde,
	        @RequestParam(required = false) String fechaHasta,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size)
	{
	    return service.obtenerPrestamosxFechayEstado(estado, fechaDesde, fechaHasta, page, size);
	}
	
	@GetMapping("/consulta_prestamo_unico/{id}")
	public ResponseEntity<LoanDTO> obtenerPrestamoRealizado(@PathVariable Long id)
	{
		return service.obtenerPrestamo(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("consulta_prestamo/{idPrestamo}/libros")
	public List<BookDTO> obtenerLibrosxPrestamo(@PathVariable Long idPrestamo)
	{
		return service.obtenerLibrosPrestamo(idPrestamo);
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