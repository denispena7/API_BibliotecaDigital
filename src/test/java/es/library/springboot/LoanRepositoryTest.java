package es.library.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import es.library.springboot.models.Loan;
import es.library.springboot.models.User;
import es.library.springboot.repositories.LoanRepository;
import es.library.springboot.repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(locations = "classpath:application-test.properties")
class LoanRepositoryTest 
{
//	@Autowired private LoanRepository loanRepo;
//	@Autowired private UserRepository userRepo;
//	
//	private User usuario;
//	private Loan prestamo;
//	
//	LocalDate desde = LocalDate.now().minusDays(20);
//	LocalDate hasta = LocalDate.now().minusDays(14);
//	
//	Pageable pageable = PageRequest.of(0, 3, Sort.by("fechaInicio").ascending());
//	
//	@BeforeEach
//	void setup()
//	{
//		usuario = User.builder()
//				.nombreUsuario("mario")
//				.build();
//		
//		userRepo.save(usuario);
//		
//		prestamo = Loan.builder()
//				.fechaInicio(desde)
//				.fechaDevolucionEsperada(hasta)
//				.fechaDevolucionReal(hasta)
//				.estado("Disponible")
//				.usuario(usuario)
//				.libros(List.of())
//				.build();
//		
//		loanRepo.save(prestamo);
//	}
//
//	@Test
//	void findByUsuarioNombreUsuario_ShouldReturnTheLoanList() 
//	{
//		Page<Loan> prestamos = loanRepo.findByUsuarioNombreUsuario("mario", pageable);
//		assertThat(prestamos).isNotEmpty();
//	}
//	
//	@Test
//	void findByUsuarioNombreUsuario_ShouldReturnEmptyList_WhenUserNotExists() 
//	{
//		Page<Loan> prestamos = loanRepo.findByUsuarioNombreUsuario("juancarlos", pageable);
//		assertThat(prestamos).isEmpty();
//	}
//	
//	@Test
//	void findByEstadoAndFechaInicioBetween_ShouldReturnTheLoanList() 
//	{
//		Page<Loan> prestamos = loanRepo.findByEstadoAndFechaInicioBetween(
//				"Disponible", desde, hasta, pageable);
//		assertThat(prestamos).isNotEmpty();
//	}
//	
//	@Test
//	void findByEstadoAndFechaInicioBetween_ShouldReturnEmptyList() 
//	{
//		Page<Loan> prestamos = loanRepo.findByEstadoAndFechaInicioBetween(
//				"No disponible", desde, hasta, pageable);
//		assertThat(prestamos).isEmpty();
//	}
}