package es.library.springboot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.library.springboot.DTOs.ApiResponse;
import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.CategoryDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.services.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController 
{
	private final CategoryService service;
	
	@GetMapping(headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<PageResponse<CategoryDTO>>> listarCategorias(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<CategoryDTO> categorias = service.obtenerCategoriasPaginadas(page, size);
		
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<CategoryDTO>>builder()
                        .data(categorias)
                        .ok(true)
                        .message("success")
                        .build()
        );
	}
	
	@GetMapping(value = "/{nombreCategoria}/books", headers = "Api-Version=1")
	public ResponseEntity<ApiResponse<PageResponse<BookDTO>>> obtenerLibros(
	        @PathVariable String nombreCategoria,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookDTO> libros = service.obtenerLibrosPorCategoria(nombreCategoria, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<BookDTO>>builder()
					.data(libros)
	                .ok(true)
	                .message("success")
	                .build()
				);
	}
}