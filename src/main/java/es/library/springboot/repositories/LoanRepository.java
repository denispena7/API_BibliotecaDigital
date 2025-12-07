package es.library.springboot.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.Loan;
import es.library.springboot.models.User;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> 
{
	List<Loan> findByUsuario(Optional<User> usuario);

	Page<Loan> findByUsuarioNombreUsuario(String nombreUsuario, Pageable pageable);
	
    Page<Loan> findByEstadoAndFechaInicioBetween(
            String estado,
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            Pageable pageable
        );
}