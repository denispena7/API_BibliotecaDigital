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
public class RatingDTO 
{
	private String nombreUsuario;
	private String tituloLibro;
	private List<Double> puntuaciones;
}