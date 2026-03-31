package es.library.springboot.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.library.springboot.models.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> 
{
	Page<Loan> findByUsuarioIdUsuario(Long idUsuario, Pageable pageable);
	
    Page<Loan> findByEstadoAndFechaInicioBetween(
            String estado,
            LocalDate fechaDesde,
            LocalDate fechaHasta,
            Pageable pageable
        );
}