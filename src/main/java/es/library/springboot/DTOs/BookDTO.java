package es.library.springboot.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO 
{
    private long idLibro;
    private String tituloLibro;
    private int anioPublicacion;
    private String sinopsisLibro;
	private String estadoLibro;
	private String portadaLibro;
	private String nombreAutor;
	private List<String> categorias;
}