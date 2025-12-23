package es.library.springboot.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="puntuaciones")
public class Rating 
{
	@EmbeddedId
	private RatingId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idUsuarioPuntFK")
	@JoinColumn(name = "idUsuarioPuntFK")
	private User usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idLibroPuntFK")
	@JoinColumn(name = "idLibroPuntFK")
	private Book libro;

	private double puntuacion;
}