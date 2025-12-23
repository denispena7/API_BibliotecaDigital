package es.library.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import es.library.springboot.models.Book;
import es.library.springboot.models.Rating;
import es.library.springboot.models.RatingId;
import es.library.springboot.models.User;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.repositories.RatingRepository;
import es.library.springboot.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.properties")
class RatingRepositoryTest 
{
    @Autowired private RatingRepository ratingRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private BookRepository bookRepo;

    private User user;
    private Book libro1;
    private Rating r1;

    @BeforeEach
    void setup() 
    {
        user = User.builder()
                .nombreUsuario("JuanMa")
                .nombreRealUsuario("Juan Manuel")
                .build();
        
        user = userRepo.save(user);

        libro1 = Book.builder()
                .tituloLibro("Los Juegos del Hambre")
                .build();
        
        libro1 = bookRepo.save(libro1);
        
        r1 = new Rating(
        		new RatingId(user.getIdUsuario(), 
        					libro1.getIdLibro()), 
        					user, libro1, 4.0);

        ratingRepo.save(r1);
    }

    @Test
    void findByTituloLibro_ShouldReturnRatingList() 
    {
        List<Rating> ratings = ratingRepo.findByLibroTituloLibro("Los Juegos del Hambre");

        assertThat(ratings).isNotNull();
        assertThat(ratings).isNotEmpty();
    }

	@Test
	void findByTituloLibro_ShouldReturnEmptyList_WhenBookTitleNotExists() 
	{
        List<Rating> ratings = ratingRepo.findByLibroTituloLibro("IT");
        assertThat(ratings).isEmpty();
	}
	
	@Test
	void findByUsuario_NombreUsuario_ShouldReturnRatingList() 
	{
		List<Rating> ratings = ratingRepo.findByUsuarioNombreUsuario("JuanMa");
		assertThat(ratings).isNotNull();
		assertThat(ratings).isNotEmpty();
	}
	
	@Test
	void findByUsuario_NombreUsuario_ShouldReturnEmptyList_WhenUserNotExists() 
	{
		List<Rating> ratings = ratingRepo.findByUsuarioNombreUsuario("carlitos");
		assertThat(ratings).isEmpty();
	}
}