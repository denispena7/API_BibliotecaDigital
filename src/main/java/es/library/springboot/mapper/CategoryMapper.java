package es.library.springboot.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import es.library.springboot.DTOs.CategoryDTO;
import es.library.springboot.models.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper 
{
	CategoryDTO toCatDTO(Category category);	
	List<CategoryDTO> toCatDTOList(List<Category> categorias);
}