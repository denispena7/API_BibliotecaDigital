package es.library.springboot.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.library.springboot.DTOs.requests.BookRequestDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.models.Book;
import es.library.springboot.models.Category;

@Mapper(componentModel = "spring")
public interface BookMapper 
{
    @Mapping(source = "autor.nombreAutor", target = "nombreAutor")
    @Mapping(target = "categorias", expression = "java(mapCategorias(book.getCategorias()))")
    BookResponseDTO toBookDTO(Book book);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "tituloLibro", source = "tituloLibro")
    @Mapping(target = "anioPublicacion", source = "anioPublicacion")
    @Mapping(target = "sinopsisLibro", source = "sinopsisLibro")
    @Mapping(target = "estadoLibro", source = "estadoLibro")
    @Mapping(target = "autor.nombreAutor", source = "nombreAutor")
    Book toBookDTO(BookRequestDTO libro);

    // Método auxiliar para transformar las categorías
    default List<String> mapCategorias(List<Category> categorias) 
    {
        return categorias.stream()
                .map(Category::getNombreCategoria)
                .collect(Collectors.toList());
    }

}