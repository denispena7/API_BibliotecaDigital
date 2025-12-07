package es.library.springbootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.library.springboot.models.Author;
import es.library.springboot.models.Book;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.services.BookService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService Tests")
class BookTest {

/*	@Mock
	private BookRepository bookRepositorio;

	@InjectMocks
	private BookService bookService;
	
	@DisplayName("Should return list of books")
	@Test
	void testObtenerListadoLibros() 
	{
		Author autor = new Author();
		autor.setNombreAutor("Eichiro Oda");
		
		List<Book> libros = List.of(
				new Book(1L, "One Piece", 1997, "Disponible", autor, ""),
				new Book(1L, "Naruto", 2000, "Disponible", autor, ""));
		
		when(bookRepositorio.findLast10OrderedByTitle()).thenReturn(libros);
		
		List<Book> result = bookService.obtenerLibros();
		
		assertFalse(result.isEmpty());
		assertEquals(2, result.size());
		assertEquals("One Piece", result.get(0).getTituloLibro());
		
		verify(bookRepositorio, times(1)).findLast10OrderedByTitle();
	}
	
	@DisplayName("Should return an empty list when there aren't books in the db")
	@Test
	void testObtenerListadoLibros_NoHayLibros() 
	{		
		when(bookRepositorio.findLast10OrderedByTitle()).thenReturn(List.of());
		
		List<Book> result = bookService.obtenerLibros();
		
		assertNotNull(result, "La lista no debe ser nula");
		assertTrue(result.isEmpty(), "La lista debería estar vacía cuando no hay libros");
		
		verify(bookRepositorio, times(1)).findLast10OrderedByTitle();
		verifyNoMoreInteractions(bookRepositorio);
	}
	
	@DisplayName("Should return Optional<Book> when book does exist")
	@Test
	void testObtenerLibro() 
	{
		// Arrange
		String titulo = "Vagabond";
		Book libro = new Book(1L, "Vagabond", 1996, "Disponible", new Author(), "");
		Optional<Book> optLibro = Optional.ofNullable(libro);
		
		when(bookRepositorio.findByTituloLibro(titulo)).thenReturn(optLibro);

		// Act
		Optional<Book> result = bookService.obtenerLibro(titulo);

		// Assert
		assertNotNull(result, "El Optional no debe ser null");
		assertTrue(result.isPresent(), "El Optional debe contener un libro");
		assertEquals(titulo, result.get().getTituloLibro(), "El título del libro no coincide");

		// Verify
		verify(bookRepositorio, times(1)).findByTituloLibro(titulo);
	    verifyNoMoreInteractions(bookRepositorio);
	}

	@DisplayName("Should return empty Optional when book does not exist")
	@Test
	void testObtenerLibro_NoExiste() 
	{
		// Arrange
		String titulo = "Libro Inexistente";
		when(bookRepositorio.findByTituloLibro(titulo)).thenReturn(Optional.empty());

		// Act
		Optional<Book> result = bookService.obtenerLibro(titulo);

		// Assert
		assertNotNull(result, "El Optional no debe ser null");
		assertTrue(result.isEmpty(), "El Optional debe estar vacío");

		// Verify
		verify(bookRepositorio, times(1)).findByTituloLibro(titulo);
		verifyNoMoreInteractions(bookRepositorio);
	}
	
	@DisplayName("Should return a book list according to the search")
	@Test
	void testObtenerLibroPorCoincidencia() 
	{
		List<Book> libros = List.of(
				new Book(1L, "Vagabond 1", 1997, "Disponible", new Author(), ""),
				new Book(2L, "Vagabond 2", 2000, "Disponible", new Author(), ""),
				new Book(3L, "Berserk", 1995, "Disponible", new Author(), ""));
		
		when(bookRepositorio.buscarPorCoincidencia("vagabond")).thenReturn(List.of(
				libros.get(0), libros.get(1)));
		
		List<Book> result = bookService.obtenerLibrosxCoincidencia("vagabond");
		
		assertNotNull(result, "La lista no debe ser nula");
		assertFalse(result.isEmpty(), "La lista no debe estar vacía");
		assertEquals(2, result.size(), "Debe haber exactamente dos libros en la lista");
		assertEquals("Vagabond 1", result.get(0).getTituloLibro(), "El título del libro no coincide");
		
		verify(bookRepositorio, times(1)).buscarPorCoincidencia("vagabond");
		verifyNoMoreInteractions(bookRepositorio);
	}
	
	@DisplayName("Should return an empty book list according to the search")
	@Test
	void testObtenerLibroPorCoincidencia_NoHayCoincidencia() 
	{
		when(bookRepositorio.buscarPorCoincidencia("naruto")).thenReturn(List.of());
		
		List<Book> result = bookService.obtenerLibrosxCoincidencia("naruto");
		
		assertNotNull(result, "La lista no debe ser nula");
		assertTrue(result.isEmpty(), "La lista no debe tener elementos");
				
		verify(bookRepositorio, times(1)).buscarPorCoincidencia("naruto");
		verifyNoMoreInteractions(bookRepositorio);
	}
	
	@DisplayName("Should return a book list from a category written by an author")
	@Test
	void testObtenerLibroPorAutoryCategoria() 
	{		
		Author autor = new Author();	
		autor.setNombreAutor("Masashi Kishimoto");
		Author autor2 = new Author();
		autor2.setNombreAutor("Eichiro Oda");
		
		List<Book> libros = List.of(
				new Book(1L, "One Piece", 1997, "Disponible", autor2, ""),
				new Book(2L, "Naruto", 2000, "Disponible", autor, ""));
		
		when(bookRepositorio.findDistinctByCategorias_NombreCategoriaAndAutor_NombreAutor("Masashi Kishimoto", "Todas"))
			.thenReturn(List.of(libros.get(1)));
		
		List<Book> result = bookService.obtenerLibrosxCategoriayAutor("Masashi Kishimoto", "Todas");
		
	    assertNotNull(result, "La lista no debe ser nula");
	    assertFalse(result.isEmpty(), "La lista no debe estar vacía");
	    assertEquals(1, result.size(), "Debe haber exactamente un libro en la lista");
	    assertEquals("Naruto", result.get(0).getTituloLibro(), "El título del libro no coincide");
		
		verify(bookRepositorio, times(1)).findDistinctByCategorias_NombreCategoriaAndAutor_NombreAutor("Masashi Kishimoto", "Todas");
	}
	
	@DisplayName("Should return an empty book list from a category written by an author who doesn't exist")
	@Test
	void testObtenerLibroPorAutoryCategoria_NoExisteAutor() 
	{		
		String autorNoExiste = "Autor inexistente";
		
		when(bookRepositorio.findDistinctByCategorias_NombreCategoriaAndAutor_NombreAutor(autorNoExiste, "Todas"))
			.thenReturn(List.of());
		
		List<Book> result = bookService.obtenerLibrosxCategoriayAutor(autorNoExiste, "Todas");
		
	    assertNotNull(result, "La lista no debe ser nula");
	    assertTrue(result.isEmpty(), "La lista no debe tener elementos");
	    assertEquals(0, result.size(), "No debe de haber libros en la lista");
		
		verify(bookRepositorio, times(1)).findDistinctByCategorias_NombreCategoriaAndAutor_NombreAutor(autorNoExiste, "Todas");
		verifyNoMoreInteractions(bookRepositorio);
	}*/
}
