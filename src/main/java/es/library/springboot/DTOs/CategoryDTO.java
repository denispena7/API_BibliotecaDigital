package es.library.springboot.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO 
{
    private long idCategoria;
    private String nombreCategoria;
    private String imagenCategoria;
}