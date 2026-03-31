package es.library.springboot.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.library.springboot.DTOs.requests.CategoryRequestDTO;
import es.library.springboot.DTOs.responses.CategoryResponseDTO;
import es.library.springboot.models.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper 
{
	CategoryResponseDTO toCatDTO(Category category);
	
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nombreCategoria", source = "nombreCategoria")
	Category toCatEnt(CategoryRequestDTO catDTO);
}