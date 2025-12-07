package es.library.springboot.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> 
{
	private List<T> contenido;
	private int pagina;
	private int tamano;
	private long totalElementos;
	private int totalPaginas;
}