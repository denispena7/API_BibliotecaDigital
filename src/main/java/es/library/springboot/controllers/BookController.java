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
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.models.Book;
import es.library.springboot.services.BookService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/libros")
public class BookController 
{
	private final BookService service;
	
	@GetMapping("/librosPaginados")
	public PageResponse<BookDTO> obtenerLibros(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size
	) 
	{
	    return service.obtenerLibrosPaginados(page, size);
	}
	
	@GetMapping("/consulta_libros/{nombre}")
	public ResponseEntity<BookDTO> obtenerLibro(@PathVariable String nombre)
	{
		return service.obtenerLibro(nombre)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/busqueda_libros/{coincidencia}")
	public List<BookDTO> obtenerBusquedaLibros(@PathVariable String coincidencia)
	{
		return service.obtenerLibrosxCoincidencia(coincidencia);
	}
	
	@GetMapping("/consulta_librosFiltrados")
	public PageResponse<BookDTO> obtenerLibrosxCatyAut(
	        @RequestParam(defaultValue = "Todas") String categoria,
	        @RequestParam(defaultValue = "Todos") String autor,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		return service.obtenerLibrosxCategoriayAutor(categoria, autor, page, size);
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