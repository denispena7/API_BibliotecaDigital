package es.library.springboot.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.models.Book;
import es.library.springboot.models.Category;

@Mapper(componentModel = "spring")
public interface BookMapper 
{
    @Mapping(source = "autor.nombreAutor", target = "nombreAutor")
    @Mapping(target = "categorias", expression = "java(mapCategorias(book.getCategorias()))")
    BookDTO toBookDTO(Book book);

    List<BookDTO> toBookDTOList(List<Book> books);

    // Método auxiliar para transformar las categorías
    default List<String> mapCategorias(List<Category> categorias) 
    {
        return categorias.stream()
                .map(Category::getNombreCategoria)
                .collect(Collectors.toList());
    }
}