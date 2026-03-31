package es.library.springboot.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.library.springboot.DTOs.requests.AuthorRequestDTO;
import es.library.springboot.DTOs.responses.AuthorResponseDTO;
import es.library.springboot.models.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper 
{
	AuthorResponseDTO toAutDTO(Author autor);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nombreAutor", source = "nombreAutor")
    @Mapping(target = "nacionalidadAutor", source = "nacionalidadAutor")
	Author toAutEnt(AuthorRequestDTO autor);
}