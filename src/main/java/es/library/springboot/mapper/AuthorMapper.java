package es.library.springboot.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import es.library.springboot.DTOs.AuthorDTO;
import es.library.springboot.models.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper 
{
	AuthorDTO toAutDTO(Author autor);
	List<AuthorDTO> toAutDTOList(List<Author> autores);
}