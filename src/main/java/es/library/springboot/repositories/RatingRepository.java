package es.library.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.Rating;
import es.library.springboot.models.RatingId;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId>
{
    List<Rating> findByLibroTituloLibro(String tituloLibro);
    List<Rating> findByUsuarioNombreUsuario(String nombreUsuario);
}