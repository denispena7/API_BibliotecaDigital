package es.library.springbootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import es.library.springboot.models.Book;
import es.library.springboot.models.Category;
import es.library.springboot.repositories.CategoryRepository;
import es.library.springboot.services.CategoryService;

@DisplayName("Test for Category Object")
@ExtendWith(MockitoExtension.class)
class CategoryTest 
{
  /*  @Mock
    private CategoryRepository catRepositorio; // simulamos la BD

    @InjectMocks
    private CategoryService catService; // se inyecta el mock en el servicio

    @DisplayName("Should add a category correctly")  // Probar que se añada una categoria
    @Test
    void testAddCategoria() 
    {
        // Arrange (preparamos los datos)
        Category expected = new Category(1, "Misterio", "http://localhost:8080/uploads/categorias/misterio.jpg");

        when(catRepositorio.save(expected)).thenReturn(expected);

        // Act (ejecutamos la función real)
        Category result = catService.addCategory(expected);

        // Assert (comprobamos)
        assertEquals(expected, result);
        verify(catRepositorio, times(1)).save(expected);
    }
    
    @DisplayName("Should return all categories")  // Probar que se devuelvan las categorias
    @Test
    void testObtenerCategorias() {
        // Arrange
        List<Category> categorias = Arrays.asList(
                new Category(1, "Misterio", ""),
                new Category(2, "Romance", "")
        );

        when(catRepositorio.findAll()).thenReturn(categorias);

        // Act
        List<Category> result = catService.obtenerCategorias();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Misterio", result.get(0).getNombreCategoria());
        verify(catRepositorio, times(1)).findAll();
    }
    
    // Probar que devuelva una lista vacía si no existen categorias
    @DisplayName("Should return empty list when no categories exist")
    @Test
    void testObtenerCategorias_NoExisteNinguna() {
        when(catRepositorio.findAll()).thenReturn(List.of());

        List<Category> result = catService.obtenerCategorias();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(catRepositorio, times(1)).findAll();
    }

    // Probar que se devuelvan los libros de una categoria especifica
    @DisplayName("Should return books of a specific category")
    @Test
    void testObtenerLibrosPorCategoria() 
    {
        // Arrange
        Book libro1 = new Book();
        libro1.setTituloLibro("El misterio del lago");
        Book libro2 = new Book();
        libro2.setTituloLibro("Sombras ocultas");

        Category categoria = new Category(1, "Misterio", "");
        categoria.setLibros(Arrays.asList(libro1, libro2));

        when(catRepositorio.findByNombreCategoria("Misterio")).thenReturn(categoria);

        // Act
        List<Book> result = catService.obtenerLibrosPorCategoria("Misterio");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("El misterio del lago", result.get(0).getTituloLibro());
        verify(catRepositorio, times(1)).findByNombreCategoria("Misterio");
    }
    
    // Probar que se devuelva una lista vacía si no existe la categoria / si la categoria no tiene libros
    @DisplayName("Should return empty list when cat not found or cat doesn't have any books")
    @Test
    void testObtenerLibrosPorCategoria_NoExistenLibrosEnCategoria() {
        when(catRepositorio.findByNombreCategoria("Fantasia")).thenReturn(null);

        List<Book> result = catService.obtenerLibrosPorCategoria("Fantasia");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(catRepositorio, times(1)).findByNombreCategoria("Fantasia");
    }
*/
}