package es.library.springboot.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingId implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long idUsuarioPuntFK;
    private Long idLibroPuntFK;
}