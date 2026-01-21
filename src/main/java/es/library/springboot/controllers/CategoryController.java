package es.library.springboot.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.CategoryRequestDTO;
import es.library.springboot.DTOs.responses.ApiResponse;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.CategoryResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController 
{
	private final CategoryService service;
	
	@PreAuthorize("hasAuthority('category:read')")
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<CategoryResponseDTO>>> listarCategorias(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<CategoryResponseDTO> categorias = service.obtenerCategoriasPaginadas(page, size);
		
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<CategoryResponseDTO>>builder()
                        .data(categorias)
                        .ok(true)
                        .message("success")
                        .build()
        );
	}
	
	@PreAuthorize("hasAuthority('category:read')")
	@GetMapping("/{nombreCategoria}/books")
	public ResponseEntity<ApiResponse<PageResponse<BookResponseDTO>>> obtenerLibros(
	        @PathVariable String nombreCategoria,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> libros = service.obtenerLibrosPorCategoria(nombreCategoria, page, size);
		
		return ResponseEntity.ok(
				ApiResponse.<PageResponse<BookResponseDTO>>builder()
					.data(libros)
	                .ok(true)
	                .message("success")
	                .build()
				);
	}
	
	@PreAuthorize("hasAuthority('category:create')")
	@PostMapping(
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
		)
	public ResponseEntity<ApiResponse<CategoryResponseDTO>> altaCategoria(
			@RequestPart("data") @Valid CategoryRequestDTO cat,
	        @RequestPart(value = "file", required = false) MultipartFile file)
	{
		CategoryResponseDTO categoria = service.crearCategoria(cat, file);
		
		return ResponseEntity.ok(
				ApiResponse.<CategoryResponseDTO>builder()
				.data(categoria)
                .ok(true)
                .message("Category created")
                .build()
			);
	}
	
	@PreAuthorize("hasAuthority('category:update')")
	@PutMapping(
			value = "/{id}", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE
		)
	public ResponseEntity<ApiResponse<CategoryResponseDTO>> actualizarCategoria(
			@PathVariable Long id, 
			@RequestPart("data") @Valid CategoryRequestDTO cat,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		CategoryResponseDTO categoriaActualizada = service.modCategoria(id, cat, file);
		
		return ResponseEntity.ok(
				ApiResponse.<CategoryResponseDTO>builder()
				.data(categoriaActualizada)
                .ok(true)
                .message("Category updated")
                .build()
			);
	}
	
	@PreAuthorize("hasAuthority('category:delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id)
	{
		service.eliminarCategoria(id);
	    return ResponseEntity.noContent().build();
	}
}