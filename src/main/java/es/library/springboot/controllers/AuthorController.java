package es.library.springboot.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.AuthorDTO;
import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.models.Author;
import es.library.springboot.services.AuthorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autores")
public class AuthorController 
{
	private final AuthorService service;
	
	@GetMapping("/consulta_autores")
	public List<AuthorDTO> listarAutores()
	{
		return service.obtenerAutores();
	}
	
	@GetMapping("/consulta_autores_paginados")
	public PageResponse<AuthorDTO> listarAutores(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		return service.obtenerAutoresPaginados(page, size);
	}

	@GetMapping("/consulta_autor/{nombreAutor}/libros")
	public List<BookDTO> obtenerLibros(@PathVariable String nombreAutor)
	{
		return service.obtenerLibrosPorAutor(nombreAutor);
	}
	
	@PostMapping("/alta_autor")
	public Author altaAutor(@RequestBody Author autor)
	{
		return service.guardarAutor(autor);
	}
	
	@PutMapping("act_autor/{id}")
	public Author actualizarAutor(@PathVariable Long id, @RequestBody Author autor)
	{
		return service.actualizar(id, autor);
	}
}