package es.library.springbootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.library.springboot.models.Author;
import es.library.springboot.models.Book;
import es.library.springboot.models.Rating;
import es.library.springboot.models.User;
import es.library.springboot.repositories.RatingRepository;
import es.library.springboot.services.RatingService;

@DisplayName("Test for Rating Object")
@ExtendWith(MockitoExtension.class)
class RatingTest 
{
/*	@Mock
	private RatingRepository ratingRepo;
	
	@InjectMocks
	private RatingService ratingServ;
	
	@DisplayName("Should return a book ratings")
	@Test
	void testObtenerRatingsxLibro()
	{
		User user = new User();
		user.setIdUsuario(1L);
		user.setNombreUsuario("Luffy");
		Author autor = new Author();
		autor.setNombreAutor("Eiichiro Oda");
		Book libro = new Book(1L, "One Piece", 1997, "Disponible", autor, "");
		
		List<Rating> punt = List.of(
				new Rating(user, libro, 5),
				new Rating(user, libro, 3)
		);
		
		when(ratingRepo.findByLibro(libro)).thenReturn(punt);
		
        // aquí podrías continuar con tu verificación:
        List<Rating> result = ratingServ.obtenerRatingsPorLibro(libro);

        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getPuntuacion());
        
        verify(ratingRepo, times(1)).findByLibro(libro);
	}
	
	@DisplayName("Should return an empty list if the book doesn't exist")
	@Test
	void testObtenerRatingxLibro_NoExisteLibro() 
	{
	    // Arrange
	    Book libro = null;

	    // Act
	    List<Rating> result = ratingServ.obtenerRatingsPorLibro(libro);

	    // Assert
	    assertNotNull(result);
	    assertTrue(result.isEmpty());

	    // Verifica que NO se llamó al repositorio
	    verifyNoInteractions(ratingRepo);
	}
	
	@DisplayName("Should return an user ratings")
	@Test
	void testObtenerRatingsxUsuario()
	{
		User user = new User(1L, "denis", 951632487, "denis@gmail.com", "", 0, "");
		Author autor = new Author();
		autor.setNombreAutor("Eiichiro Oda");
		
		Book libro = new Book(1L, "One Piece", 1997, "Disponible", autor, "");
		
		List<Rating> punt = List.of(
				new Rating(user, libro, 2),
				new Rating(user, libro, 4)
		);
		
		when(ratingRepo.findByUsuario(user)).thenReturn(punt);
		
        // aquí podrías continuar con tu verificación:
        List<Rating> result = ratingServ.obtenerRatingsPorUsuario(user);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getPuntuacion());
        assertEquals("One Piece", result.get(0).getLibro().getTituloLibro());
        assertEquals("denis", result.get(0).getUsuario().getNombreUsuario());
        
        verify(ratingRepo, times(1)).findByUsuario(user);
	}
	
	@DisplayName("Should return an empty list if the user doesn't exist")
	@Test
	void testObtenerRatingxUsuario_NoExisteUsuario() 
	{
	    // Arrange
	    User usuario = null;

	    // Act
	    List<Rating> result = ratingServ.obtenerRatingsPorUsuario(usuario);

	    // Assert
	    assertNotNull(result);
	    assertTrue(result.isEmpty());

	    // Verifica que NO se llamó al repositorio
	    verifyNoInteractions(ratingRepo);
	}
	
	@DisplayName("Should fail when creating rating with null user or book IDs")
	@Test
	void testCrearRatingInvalido() {
	    User user = new User();
	    Book book = new Book();

	    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
	        () -> new Rating(user, book, 5));

	    assertEquals("Usuario o su ID no pueden ser nulos", ex.getMessage());
	}
*/

}
