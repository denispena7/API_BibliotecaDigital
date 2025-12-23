package es.library.springboot.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.CategoryDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.mapper.BookMapper;
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
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	Pageable pageable;
	
	@Transactional(readOnly = true)
	public PageResponse<CategoryDTO> obtenerCategoriasPaginadas(int page, int size)
	{
		pageable = PageableService.getPageable(page, size, "nombreCategoria");
		
        Page<Category> pageAutores = catRepositorio.findAll(pageable);

        return pageMapper.toPageResponse(pageAutores, catMapper::toCatDTO);
	}
	
	@Transactional(readOnly = true)
	public PageResponse<BookDTO> obtenerLibrosPorCategoria(String nCategoria, int page, int size) 
	{
		catRepositorio.findByNombreCategoria(nCategoria)
	        .orElseThrow(() -> new EntityNotFoundException("Category not found"));
		
		pageable = PageableService.getPageable(page, size, "tituloLibro");

	    Page<Book> pageLibros = bookRepositorio.findByCategoriasNombreCategoria(pageable, nCategoria);

	    return pageMapper.toPageResponse(pageLibros, bookMapper::toBookDTO);
	}
}