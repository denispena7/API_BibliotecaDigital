package es.library.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import es.library.springboot.models.Author;
import es.library.springboot.models.Book;
import es.library.springboot.models.Category;
import es.library.springboot.repositories.AuthorRepository;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.repositories.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.properties")
class BookRepositoryTest 
{
    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    private Book book1;
    private Book book2;

    private Author author1;
    private Author author2;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setup()
    {
        author1 = Author.builder()
                .nombreAutor("Stephen King")
                .nacionalidadAutor("USA")
                .imagenAutor("")
                .build();

        author2 = Author.builder()
                .nombreAutor("George Orwell")
                .nacionalidadAutor("UK")
                .imagenAutor("")
                .build();

        authorRepo.save(author1);
        authorRepo.save(author2);

        category1 = Category.builder()
                .nombreCategoria("Terror")
                .build();

        category2 = Category.builder()
                .nombreCategoria("Distopía")
                .build();

        categoryRepo.save(category1);
        categoryRepo.save(category2);

        book1 = Book.builder()
                .tituloLibro("It")
                .autor(author1)
                .categorias(List.of(category1))
                .build();

        book2 = Book.builder()
                .tituloLibro("1984")
                .autor(author2)
                .categorias(List.of(category2))
                .build();

        bookRepo.save(book1);
        bookRepo.save(book2);
    }

    @Test
    void findByTituloLibro_ShouldReturnBook()
    {
        var optBook = bookRepo.findByTituloLibro("It");

        assertTrue(optBook.isPresent());
        assertEquals("Stephen King", optBook.get().getAutor().getNombreAutor());
    }

    @Test
    void findByTituloLibro_ShouldReturnEmpty_WhenNotExists()
    {
        var optBook = bookRepo.findByTituloLibro("Unknown");

        assertFalse(optBook.isPresent());
    }

    @Test
    void findByTituloLibroContaining_ShouldReturnPage()
    {
        Pageable pageable = PageRequest.of(0, 10);

        var page = bookRepo.findByTituloLibroContaining("1", pageable);

        assertFalse(page.isEmpty());
        assertEquals("1984", page.getContent().get(0).getTituloLibro());
    }

    @Test
    void findByAutorNombreAutor_ShouldReturnBooks()
    {
        Pageable pageable = PageRequest.of(0, 10);

        var page = bookRepo.findByAutorNombreAutor(pageable, "Stephen King");

        assertEquals(1, page.getTotalElements());
        assertEquals("It", page.getContent().get(0).getTituloLibro());
    }

    @Test
    void findByCategoriasNombreCategoria_ShouldReturnBooks()
    {
        Pageable pageable = PageRequest.of(0, 10);

        var page = bookRepo.findByCategoriasNombreCategoria(pageable, "Terror");

        assertEquals(1, page.getTotalElements());
        assertEquals("It", page.getContent().get(0).getTituloLibro());
    }

    @Test
    void findDistinctByCategoriasAndAutor_ShouldReturnBooks()
    {
        Pageable pageable = PageRequest.of(0, 10);

        var page = bookRepo.findDistinctByCategorias_NombreCategoriaAndAutor_NombreAutor(
                "Terror", "Stephen King", pageable);

        assertEquals(1, page.getTotalElements());
        assertEquals("It", page.getContent().get(0).getTituloLibro());
    }

    @Test
    void existsByTituloLibro_ShouldReturnTrue()
    {
        boolean exists = bookRepo.existsByTituloLibro("It");

        assertTrue(exists);
    }

    @Test
    void existsByTituloLibro_ShouldReturnFalse()
    {
        boolean exists = bookRepo.existsByTituloLibro("Unknown");

        assertFalse(exists);
    }

    @Test
    void existsByIdLibro_ShouldReturnTrue()
    {
        boolean exists = bookRepo.existsByIdLibro(book1.getIdLibro());

        assertTrue(exists);
    }

    @Test
    void findAllByOrderByTituloLibroAsc_ShouldReturnOrdered()
    {
        Pageable pageable = PageRequest.of(0, 10);

        var page = bookRepo.findAllByOrderByTituloLibroAsc(pageable);

        assertEquals(2, page.getTotalElements());
        assertEquals("1984", page.getContent().get(0).getTituloLibro());
        assertEquals("It", page.getContent().get(1).getTituloLibro());
    }

    @Test
    void findByIdLibroIn_ShouldReturnList()
    {
        List<Integer> ids = List.of(book1.getIdLibro().intValue(), book2.getIdLibro().intValue());

        var books = bookRepo.findByIdLibroIn(ids);

        assertEquals(2, books.size());
    }
}
