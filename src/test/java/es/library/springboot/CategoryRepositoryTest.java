package es.library.springboot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import es.library.springboot.models.Category;
import es.library.springboot.repositories.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.properties")
class CategoryRepositoryTest 
{
	@Autowired
	private CategoryRepository categoryRepo;
	
    private Category misteryCategory;
    private Category romanceCategory;

    @BeforeEach
    void setup() 
    {
        misteryCategory = Category.builder()
                .nombreCategoria("Misterio")
                .imagenCategoria("misterio.png")
                .build();

        romanceCategory = Category.builder()
                .nombreCategoria("Romance")
                .imagenCategoria("romance.png")
                .build();

        categoryRepo.save(misteryCategory);
        categoryRepo.save(romanceCategory);
    }
	
	@Test
	void findByNombreCategoria_ShouldReturnTheCategory() 
	{
		var optCat = categoryRepo.findByNombreCategoria("Misterio");
		
		assertTrue(optCat.isPresent());
		assertEquals("Misterio", optCat.get().getNombreCategoria());
	}
	
//    @Test
//    void findByNombreCategoria_ShouldReturnEmpty_WhenNotExists() 
//    {
//        var result = categoryRepo.findByNombreCategoria("Terror");
//
//        assertThat(result).isNotPresent();
//    }
}