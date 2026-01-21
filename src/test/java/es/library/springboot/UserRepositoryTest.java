package es.library.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import es.library.springboot.models.User;
import es.library.springboot.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserRepositoryTest 
{
//	@Autowired
//	private UserRepository userRepo;
//	
//	private User u1;
//	private User u2;
//
//    @BeforeEach
//    void setup() 
//    {
//    	u1 = User.builder()
//				.nombreUsuario("JuanMa")
//				.nombreRealUsuario("Juan Manuel")
//				.apellidosUsuario("Gonzáles")
//				.direccionUsuario("Avda. Diego Martinez Barrio S/N")
//				.ciudadUsuario("Sevilla")
//				.localidadUsuario("Sevilla")
//				.cpUsuario(41013)
//				.telefonoUsuario(694213258)
//				.emailUsuario("juanma@gmail.com")
//				.claveUsuario("hola123")
//				.iconoUsuario("")
//				.puntuaciones(List.of())
//				.build();
//		
//		u2 = User.builder()
//				.nombreUsuario("Joselu")
//				.nombreRealUsuario("Jose Luis")
//				.apellidosUsuario("Mato")
//				.direccionUsuario("Avda. Ramón y Cajal S/N")
//				.ciudadUsuario("Sevilla")
//				.localidadUsuario("Sevilla")
//				.cpUsuario(41013)
//				.telefonoUsuario(694213258)
//				.emailUsuario("joselu@gmail.com")
//				.claveUsuario("adios123")
//				.iconoUsuario("")
//				.puntuaciones(List.of())
//				.build();
//		
//		userRepo.save(u1);
//		userRepo.save(u2);
//    }
//
//	@Test
//	void findByNombreUsuario_ShouldReturnTheUser() 
//	{		
//		var optUsr = userRepo.findByNombreUsuario("Joselu");
//		
//		assertTrue(optUsr.isPresent());
//		assertEquals("Joselu", optUsr.get().getNombreUsuario());
//	}
//
//	@Test
//	void findByNombreUsuario_ShouldReturnEmpty_WhenNotExists()
//	{
//		var optUsr = userRepo.findByNombreUsuario("mike99");
//		
//		assertTrue(optUsr.isEmpty());
//	}
}
