package es.library.springbootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.library.springboot.models.User;
import es.library.springboot.repositories.UserRepository;
import es.library.springboot.services.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService test")
class UserTest 
{
	@Mock
	UserRepository userRepo;
	
	@InjectMocks
	UserService userServ;
	
/*	@DisplayName("Should return a list of users")
	@Test
	void testObtenerUsuarios()
	{
		User usr = new User(1L, "denis", 951632487, "denis@gmail.com", "", 0, "");
		
		when(userRepo.findAll()).thenReturn(List.of(usr));
		
		List<User> usuarios = userServ.obtenerUsuarios();
		
		assertNotNull(usuarios, "La lista no debe ser nula");
		assertFalse(usuarios.isEmpty(), "La lista de usuarios no debe estar vacía");
		assertEquals(1, usuarios.size());
		assertEquals("denis@gmail.com", usuarios.get(0).getEmailUsuario());
		
        // Alternative verification options
		verify(userRepo).findAll();
        verify(userRepo, atLeastOnce()).findAll();
        verify(userRepo, atLeast(1)).findAll();
        verifyNoMoreInteractions(userRepo);
	}
	*/
/*	@DisplayName("Should return an empty list of users if there aren't in the db")
	@Test
	void testObtenerUsuarios_NoExisten()
	{	
		when(userRepo.findAll()).thenReturn(List.of());
		
		List<User> usuarios = userServ.obtenerUsuarios();
		
		assertNotNull(usuarios, "La lista no debe ser nula");
		assertTrue(usuarios.isEmpty(), "La lista de usuarios debe estar vacía");
		
        // Alternative verification options
		verify(userRepo).findAll();
        verify(userRepo, atLeastOnce()).findAll();
        verify(userRepo, atLeast(1)).findAll();
        verifyNoMoreInteractions(userRepo);
	}
*/	
/*	@DisplayName("Should return Optional<User> when user does exist")
	@Test
	void testObtenerUsuario()
	{	
		// Arrange
		String nombreU = "denis";
		User usr = new User(1L, "denis", 951632487, "denis@gmail.com", "", 0, "");
		Optional<User> optUsr = Optional.ofNullable(usr);
		
		when(userRepo.findByNombreUsuario(eq(nombreU))).thenReturn(optUsr);

		// Act
		Optional<User> result = userServ.obtenerUsuario("denis");

		// Assert
		assertNotNull(result, "El Optional no debe ser null");
		assertTrue(result.isPresent(), "El Optional debe contener un libro");
		assertEquals(nombreU, result.get().getNombreUsuario(), "El título del libro no coincide");

		// Verify
		verify(userRepo).findByNombreUsuario("denis");
		verify(userRepo, times(1)).findByNombreUsuario(nombreU);
	    verifyNoMoreInteractions(userRepo);
	}
	*/
	/*@DisplayName("Should return an empty Optional<User> when user doesn't exist")
	@Test
	void testObtenerUsuario_NoExiste()
	{	
		// Arrange
		String nombreU = "hola";
		
		when(userRepo.findByNombreUsuario(nombreU)).thenReturn(Optional.empty());

		// Act
		Optional<User> result = userServ.obtenerUsuario("hola");

		// Assert
		assertNotNull(result, "El Optional no debe ser null");
		assertTrue(result.isEmpty(), "El Optional debe estar vacío");

		// Verify
		verify(userRepo).findByNombreUsuario("hola");
		verify(userRepo, times(1)).findByNombreUsuario(nombreU);
	    verifyNoMoreInteractions(userRepo);
	}*/
}