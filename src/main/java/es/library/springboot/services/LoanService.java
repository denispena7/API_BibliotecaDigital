package es.library.springboot.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.LoanDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.LoanMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Loan;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanService 
{
	private final LoanRepository loanRepositorio;
	private final BookRepository bookRepositorio;
	private final LoanMapper loanMapper;
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	public PageResponse<LoanDTO> obtenerPrestamos(int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size, Sort.by("fechaInicio").ascending());
        Page<Loan> pagePrestamos = loanRepositorio.findAll(pageable);

        return pageMapper.toPageResponse(pagePrestamos, loanMapper::toLoanDTO);
	}
	
	public PageResponse<LoanDTO> obtenerPrestamosxUsuario(String nU, int page, int size)
	{
		Pageable pageable = PageRequest.of(page, size, Sort.by("fechaInicio").ascending());
        Page<Loan> pagePrestamos = loanRepositorio.findByUsuarioNombreUsuario(nU, pageable);

        return pageMapper.toPageResponse(pagePrestamos, loanMapper::toLoanDTO);
	}
	
	public PageResponse<LoanDTO> obtenerPrestamosxFechayEstado(
			String estado, String fechaDesde, String fechaHasta, int page, int size) 
	{
	    Pageable pageable = PageRequest.of(page, size, Sort.by("fechaInicio").ascending());
	    
	    // Valores por defecto
	    if (fechaHasta == null || fechaHasta.isBlank()) {
	        fechaHasta = LocalDate.now().toString();  // hoy
	    }

	    if (fechaDesde == null || fechaDesde.isBlank()) {
	        fechaDesde = LocalDate.now().minusDays(7).toString();  // hoy - 7 días
	    }

	    LocalDate desde = LocalDate.parse(fechaDesde);
	    LocalDate hasta = LocalDate.parse(fechaHasta);

	    Page<Loan> pageLoans =
	            loanRepositorio.findByEstadoAndFechaInicioBetween(estado, desde, hasta, pageable);

	    return pageMapper.toPageResponse(pageLoans, loanMapper::toLoanDTO);
	}
	
	public Optional<LoanDTO> obtenerPrestamo(Long id)
	{
        return loanRepositorio.findById(id)
                .map(loanMapper::toLoanDTO);
	}
	
	public List<BookDTO> obtenerLibrosPrestamo(Long id)
	{
		return bookRepositorio.findByPrestamoIdPrestamo(id)
				.stream()
				.map(bookMapper::toBookDTO)
				.toList();
	}
	
	public Loan guardarPrestamo(Loan prestamo)
	{
		return loanRepositorio.save(prestamo);
	}
	
	public Loan actualizar(Long id, Loan prestamoDetalles) 
	{
	    return loanRepositorio.findById(id)
	        .map(prestamo -> {
	            prestamo.setFechaInicio(prestamoDetalles.getFechaInicio());
	            prestamo.setFechaDevolucionEsperada(prestamoDetalles.getFechaDevolucionEsperada());
	            prestamo.setFechaDevolucionReal(prestamoDetalles.getFechaDevolucionReal());
	            prestamo.setEstado(prestamoDetalles.getEstado());
	            return loanRepositorio.save(prestamo);
	        })
	        .orElseThrow(() -> new RuntimeException("Libro no encontrado con id " + id));
	}
}