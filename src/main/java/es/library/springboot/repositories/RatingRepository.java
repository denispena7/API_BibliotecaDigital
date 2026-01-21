package es.library.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.Rating;
import es.library.springboot.models.RatingId;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId>
{
    @Query("""
    	    select avg(r.puntuacion)
    	    from Rating r
    	    where r.libro.tituloLibro = :titulo
    	""")
    Double findAvgRatingByLibro(@Param("titulo") String tituloLibro);

    Optional<Rating> findByUsuarioIdUsuarioAndLibroIdLibro(Long idUsuario, Long idLibro);
}