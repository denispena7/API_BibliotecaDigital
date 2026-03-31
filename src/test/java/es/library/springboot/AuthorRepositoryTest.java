package es.library.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import es.library.springboot.models.Author;
import es.library.springboot.repositories.AuthorRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthorRepositoryTest 
{
	@Autowired
	private AuthorRepository autRepo;
	
	private Author aut1;
	private Author aut2;
	
	@BeforeEach
	void setup()
	{
		aut1 = Author.builder()
				.nombreAutor("Stephen King")
				.nacionalidadAutor("Estados Unidos")
				.imagenAutor("")
				.build();
		
		aut2 = Author.builder()
				.nombreAutor("Scott Snyder")
				.nacionalidadAutor("Estados Unidos")
				.imagenAutor("")
				.build();
		
		autRepo.save(aut1);
		autRepo.save(aut2);
	}
	
	@Test
	void findByNombreAutor_ShouldReturnTheAuthor() 
	{
		var optAut = autRepo.findByNombreAutor("Scott Snyder");
		
		assertTrue(optAut.isPresent());
		assertEquals("Estados Unidos", optAut.get().getNacionalidadAutor());
	}
	
	@Test
	void findByNombreAutor_ShouldReturnEmpty_WhenNotExists()
	{
		var optAut = autRepo.findByNombreAutor("Julio Verne");
		
		assertFalse(optAut.isPresent());
	}
}