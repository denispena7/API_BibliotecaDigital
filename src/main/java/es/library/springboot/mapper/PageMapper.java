package es.library.springboot.mapper;

import java.util.function.Function;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import es.library.springboot.DTOs.responses.PageResponse;

@Mapper(componentModel = "spring")
public interface PageMapper
{
    default <E, D> PageResponse<D> toPageResponse(
            Page<E> page,
            Function<E, D> mapper
    ) {
        return new PageResponse<>(
                page.getContent()
                        .stream()
                        .map(mapper)
                        .toList(),
                page.getNumber(),           
                page.getSize(),             
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSort().isSorted(),
                page.isFirst(),
                page.isLast()
        );
    }
}