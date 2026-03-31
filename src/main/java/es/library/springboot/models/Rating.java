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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
	@JoinColumn(name = "idUsuarioPuntFK", nullable = false)
	private User usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idLibroPuntFK")
	@JoinColumn(name = "idLibroPuntFK", nullable = false)
	private Book libro;

	private double puntuacion;
}