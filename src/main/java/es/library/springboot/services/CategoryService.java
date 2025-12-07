package es.library.springboot.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.CategoryDTO;
import es.library.springboot.DTOs.PageResponse;
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
public class CategoryService 
{
	private final CategoryRepository catRepositorio;
	private final BookRepository bookRepositorio;
	private final CategoryMapper catMapper;
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	public List<CategoryDTO> obtenerCategorias()
	{
		return catMapper.toCatDTOList(
				catRepositorio.findAll(Sort.by(Sort.Direction.ASC, "nombreCategoria"))
		);
	}
	
	public PageResponse<CategoryDTO> obtenerCategoriasPaginadas(int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size, Sort.by("nombreCategoria").ascending());
        Page<Category> pageAutores = catRepositorio.findAll(pageable);

        return pageMapper.toPageResponse(pageAutores, catMapper::toCatDTO);
	}
	
	public PageResponse<BookDTO> obtenerLibrosPorCategoria(String nCategoria, int page, int size) 
	{
	    Pageable pageable = PageRequest.of(page, size, Sort.by("tituloLibro").ascending());

	    Page<Book> pageLibros = bookRepositorio.findByCategoriasNombreCategoria(pageable, nCategoria);

	    return pageMapper.toPageResponse(pageLibros, bookMapper::toBookDTO);
	}
}