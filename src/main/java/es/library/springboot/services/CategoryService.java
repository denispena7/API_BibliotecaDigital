package es.library.springboot.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.CategoryRequestDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.CategoryResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.exceptions.ValidateException;
import es.library.springboot.mapper.CategoryMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Book;
import es.library.springboot.models.Category;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService 
{
	private final CategoryRepository catRepositorio;
	private final BookRepository bookRepositorio;
	private final CategoryMapper catMapper;
	private final PageMapper pageMapper;
	
	Pageable pageable;
	private final DTOResponseBuilder responseBuilder;
	
	private final FileStorageService fileStorageService;
	private final StorageProperties storageProperties;

	
	@Transactional(readOnly = true)
	public PageResponse<CategoryResponseDTO> obtenerCategoriasPaginadas(int page, int size)
	{
		pageable = PageableService.getPageable(page, size, "nombreCategoria");
		
        Page<Category> pageAutores = catRepositorio.findAll(pageable);

        return pageMapper.toPageResponse(pageAutores, responseBuilder::buildCatResponse);
	}
	
	@Transactional(readOnly = true)
	public PageResponse<BookResponseDTO> obtenerLibrosPorCategoria(String nCategoria, int page, int size) 
	{
		catRepositorio.findByNombreCategoria(nCategoria)
	        .orElseThrow(() -> new EntityNotFoundException("Category not found"));
		
		pageable = PageableService.getPageable(page, size, "tituloLibro");

	    Page<Book> pageLibros = bookRepositorio.findByCategoriasNombreCategoria(pageable, nCategoria);

	    return pageMapper.toPageResponse(pageLibros, responseBuilder::buildBookResponse);
	}
	
	@Transactional
	public CategoryResponseDTO crearCategoria(CategoryRequestDTO cat, MultipartFile file) 
	{
	    if (catRepositorio.existsByNombreCategoria(cat.nombreCategoria())) {
	        throw new ValidateException("Category already exists");
	    }
	    
	    Category catEntity = catMapper.toCatEnt(cat);
	    
	    if (file != null && !file.isEmpty()) 
	    {
	    	String filename = fileStorageService.saveFile(file, storageProperties.getCategoryPath());
	    	catEntity.setImagenCategoria(filename);	    	
	    }
	    
	    Category saved = catRepositorio.save(catEntity);

	    return responseBuilder.buildCatResponse(saved);
	}

	
	@Transactional
	public CategoryResponseDTO modCategoria(Long id, CategoryRequestDTO cat, MultipartFile file) 
	{
	    Category categoria = catRepositorio.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Category not found"));

	    categoria.setNombreCategoria(cat.nombreCategoria());

	    updateCategoryImage(categoria, file);

	    Category updated = catRepositorio.save(categoria);

	    return responseBuilder.buildCatResponse(updated);

	}
	
	@Transactional
	public void eliminarCategoria(Long id)
	{
		Category categoria = catRepositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found"));
		
		catRepositorio.delete(categoria);
		
		fileStorageService.deleteFileIfExists(categoria.getImagenCategoria(), storageProperties.getCategoryPath());
	}
	
	
	private void updateCategoryImage(Category categoria, MultipartFile file) 
	{
	    if (file == null || file.isEmpty()) return;

	    fileStorageService.deleteFileIfExists(
	            categoria.getImagenCategoria(),
	            storageProperties.getCategoryPath()
	    );

	    String filename = fileStorageService.saveFile(
	            file,
	            storageProperties.getCategoryPath()
	    );

	    categoria.setImagenCategoria(filename);
	}
}