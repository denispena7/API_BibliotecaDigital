package es.library.springboot.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.library.springboot.DTOs.BookDTO;
import es.library.springboot.DTOs.LoanDTO;
import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.LoanMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Loan;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.repositories.LoanRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanService 
{
	private final LoanRepository loanRepositorio;
	private final BookRepository bookRepositorio;
	private final LoanMapper loanMapper;
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	Pageable pageable;
	
	@Transactional(readOnly = true)
	public PageResponse<LoanDTO> obtenerPrestamos(int page, int size)
	{
		pageable = PageableService.getPageable(page, size, "fechaInicio");
		
        Page<Loan> pagePrestamos = loanRepositorio.findAll(pageable);

        return pageMapper.toPageResponse(pagePrestamos, loanMapper::toLoanDTO);
	}
	
	@Transactional(readOnly = true)
	public PageResponse<LoanDTO> obtenerPrestamosxUsuario(String nU, int page, int size)
	{
		pageable = PageableService.getPageable(page, size, "fechaInicio");
		
        Page<Loan> pagePrestamos = loanRepositorio.findByUsuarioNombreUsuario(nU, pageable);

        return pageMapper.toPageResponse(pagePrestamos, loanMapper::toLoanDTO);
	}
	
	@Transactional(readOnly = true)
	public PageResponse<LoanDTO> obtenerPrestamosxFechayEstado(
			String estado, String fechaDesde, String fechaHasta, int page, int size) 
	{
		pageable = PageableService.getPageable(page, size, "fechaInicio");
	    
	    // Valores por defecto
	    if (fechaHasta == null || fechaHasta.isBlank()) {
	        fechaHasta = LocalDate.now().toString();
	    }

	    if (fechaDesde == null || fechaDesde.isBlank()) {
	        fechaDesde = LocalDate.now().minusDays(7).toString();
	    }

	    LocalDate desde = LocalDate.parse(fechaDesde);
	    LocalDate hasta = LocalDate.parse(fechaHasta);

	    Page<Loan> pageLoans =
	            loanRepositorio.findByEstadoAndFechaInicioBetween(estado, desde, hasta, pageable);

	    return pageMapper.toPageResponse(pageLoans, loanMapper::toLoanDTO);
	}
	
	@Transactional(readOnly = true)
	public LoanDTO obtenerPrestamo(Long id)
	{
        return loanRepositorio.findById(id)
                .map(loanMapper::toLoanDTO)
                .orElseThrow(() ->
                		new EntityNotFoundException("Loan not found"));
	}
	
	@Transactional(readOnly = true)
	public List<BookDTO> obtenerLibrosPrestamo(Long id)
	{
		loanRepositorio.findById(id)
		.orElseThrow(() -> new EntityNotFoundException("Loan not found"));
		
		return bookRepositorio.findByPrestamoIdPrestamo(id).stream()
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