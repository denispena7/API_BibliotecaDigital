package es.library.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.CategoryDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.services.CategoryService;

@RestController
@RequestMapping("/categorias")
public class CategoryController 
{
	@Autowired
	private CategoryService service;
	
	@GetMapping("/consulta_categorias")
	public List<CategoryDTO> obtenerCategorias()
	{
		return service.obtenerCategorias();
	}
	
	@GetMapping("/consulta_categorias_paginadas")
	public PageResponse<CategoryDTO> listarCategorias(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		return service.obtenerCategoriasPaginadas(page, size);
	}
	
	@GetMapping("/consultaLibrosCategoria")
	public PageResponse<BookDTO> obtenerLibros(
	        @RequestParam String nombreCategoria,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		return service.obtenerLibrosPorCategoria(nombreCategoria, page, size);
	}
}