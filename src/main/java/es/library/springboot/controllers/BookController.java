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
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.models.Book;
import es.library.springboot.services.BookService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController 
{
	private final BookService service;
	
//	@GetMapping(headers = "Api-Version=1")
//	public ResponseEntity<ApiResponse<PageResponse<BookDTO>>> obtenerLibros(
//	        @RequestParam(defaultValue = "0") int page,
//	        @RequestParam(defaultValue = "10") int size
//	) 
//	{
//		PageResponse<BookDTO> libros = service.obtenerLibrosPaginados(page, size);
//		
//	    return ResponseEntity.ok(
//	    		ApiResponse.<PageResponse<BookDTO>>builder()
//	    		.data(libros)
//	    		.ok(true)
//	    		.message("success")
//	    		.build());
//	}
	
	@GetMapping(value = "/{id}", headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<BookDTO>> obtenerLibro(@PathVariable Long id)
	{
		BookDTO book = service.obtenerLibro(id);
		
		return ResponseEntity.ok(
				ApiResponse.<BookDTO>builder()
				.data(book)
				.ok(true)
				.message("success")
				.build());
	}
	
	@GetMapping(value = "/search/{coincidencia}", headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<List<BookDTO>>> obtenerBusquedaLibros(@PathVariable String coincidencia)
	{
		List<BookDTO> books = service.obtenerLibrosxCoincidencia(coincidencia);
		
		return ResponseEntity.ok(
				ApiResponse.<List<BookDTO>>builder()
				.data(books)
				.ok(true)
				.message("success")
				.build());
	}
	
	@GetMapping(headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<PageResponse<BookDTO>>> obtenerLibrosxCatyAut(
	        @RequestParam(required = false) String categoria,
	        @RequestParam(required = false) String autor,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookDTO> books = service.obtenerLibrosxCategoriayAutor(categoria, autor, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<BookDTO>>builder()
				.data(books)
				.ok(true)
				.message("success")
				.build());
	}
	
	@PostMapping("/alta_libro")
	public Book altaLibro(@RequestBody Book libro)
	{
		return service.guardarLibro(libro);
	}
	
	@PutMapping("act_libro/{id}")
	public Book actualizarLibro(@PathVariable Long id, @RequestBody Book libro)
	{
		return service.actualizar(id, libro);
	}
}