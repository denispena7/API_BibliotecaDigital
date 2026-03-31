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
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.CategoryResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.DTOs.responses.WraperResponse;
import es.library.springboot.controllers.api.CategoryApi;
import es.library.springboot.controllers.base.BaseController;
import es.library.springboot.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController extends BaseController implements CategoryApi
{
	private final CategoryService service;
	
	@Override
	@PreAuthorize("hasAuthority('category:read')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WraperResponse<PageResponse<CategoryResponseDTO>>> listarCategorias(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<CategoryResponseDTO> categorias = service.obtenerCategoriasPaginadas(page, size);
		
        return ok(categorias, "success");
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('category:read')")
	@GetMapping(value = "/{nombreCategoria}/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WraperResponse<PageResponse<BookResponseDTO>>> obtenerLibros(
			@PathVariable String nombreCategoria,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)
	{
		PageResponse<BookResponseDTO> libros = service.obtenerLibrosPorCategoria(nombreCategoria, page, size);
		
		return ok(libros, "success");
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('category:create')")
	@PostMapping(
		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		    produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<WraperResponse<CategoryResponseDTO>> altaCategoria(
			@RequestPart("data") @Valid CategoryRequestDTO cat,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		CategoryResponseDTO categoria = service.crearCategoria(cat, file);

        return created(categoria, "Category created");
	}
	
	
	@Override
	@PreAuthorize("hasAuthority('category:update')")
	@PutMapping(
			value = "/{id}", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
		)
	public ResponseEntity<WraperResponse<CategoryResponseDTO>> actualizarCategoria(
			@PathVariable Long id, 
			@RequestPart("data") @Valid CategoryRequestDTO cat,
			@RequestPart(value = "file", required = false) MultipartFile file)
	{
		CategoryResponseDTO categoriaActualizada = service.modCategoria(id, cat, file);
		
		return ok(categoriaActualizada, "Category updated");
	}
	
	
	@PreAuthorize("hasAuthority('category:delete')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id)
	{
		service.eliminarCategoria(id);
	    return noContent();
	}
}