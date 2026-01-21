package es.library.springboot.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.library.springboot.DTOs.requests.LoanRequestDTO;
import es.library.springboot.DTOs.requests.LoanUptRequestDTO;
import es.library.springboot.DTOs.responses.BookResponseDTO;
import es.library.springboot.DTOs.responses.LoanResponseDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.config.SecurityUtils;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.mapper.BookMapper;
import es.library.springboot.mapper.LoanMapper;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.models.Book;
import es.library.springboot.models.Loan;
import es.library.springboot.models.User;
import es.library.springboot.repositories.BookRepository;
import es.library.springboot.repositories.LoanRepository;
import es.library.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanService 
{
	private final LoanRepository loanRepositorio;
	private final BookRepository bookRepositorio;
	private final UserRepository userRepositorio;
	private final LoanMapper loanMapper;
	private final BookMapper bookMapper;
	private final PageMapper pageMapper;
	
	Pageable pageable;
	
	@Transactional(readOnly = true)
	public PageResponse<LoanResponseDTO> obtenerPrestamosxUsuario(int page, int size)
	{		
		User user = SecurityUtils.getCurrentUser();
		
		pageable = PageableService.getPageable(page, size, "fechaInicio");
		
        Page<Loan> pagePrestamos = loanRepositorio.findByUsuarioIdUsuario(user.getIdUsuario(), pageable);

        return pageMapper.toPageResponse(pagePrestamos, loanMapper::toLoanDTO);
	}
	
	@Transactional(readOnly = true)
	public PageResponse<LoanResponseDTO> obtenerPrestamosxFechayEstado(
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
	public LoanResponseDTO obtenerPrestamo(Long id)
	{
	    Loan loan = loanRepositorio.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Loan not found"));

	    autorizar(loan);

	    return loanMapper.toLoanDTO(loan);
	}

	
	@Transactional(readOnly = true)
	public PageResponse<BookResponseDTO> obtenerLibrosPrestamo(Long id, int page, int size)
	{
	    Loan loan = loanRepositorio.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Loan not found"));

	    autorizar(loan);
		
		pageable = PageableService.getPageable(page, size, "tituloLibro");
		
        Page<Book> pageBooks = bookRepositorio.findByPrestamoIdPrestamo(id, pageable);

        return pageMapper.toPageResponse(pageBooks, bookMapper::toBookDTO);
	}

	@Transactional
	public LoanResponseDTO nuevoPrestamo(LoanRequestDTO prestamo) 
	{
		User user = userRepositorio.findById(prestamo.idUsuario())
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		
		
		LoanDateValidator.validate(prestamo.fechaInicio());
		
		Loan nuevoPrestamo = loanMapper.toLoanEntity(prestamo);
		
		
		nuevoPrestamo.setUsuario(user);
		nuevoPrestamo.setLibros(getBooks(prestamo.libros()));
		autorizar(nuevoPrestamo);
		
		Loan guardado = loanRepositorio.save(nuevoPrestamo);
		
		return loanMapper.toLoanDTO(guardado);
	}
	
	public LoanResponseDTO actualizarPrestamo(Long id, LoanUptRequestDTO prestamo) 
	{
		Loan prest = loanRepositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Loan not found"));
		
		autorizar(prest);
		
		prest.setFechaDevolucionReal(LocalDate.now());
		prest.setEstado(prestamo.estado());
		
		Loan updated = loanRepositorio.save(prest);
		
		return loanMapper.toLoanDTO(updated);
	}
	
	private void autorizar(Loan loan) 
	{
		User principal = SecurityUtils.getCurrentUser();
		
		boolean isOwner = loan.getUsuario().getIdUsuario()
				.equals(principal.getIdUsuario());
		
		boolean canReadAny = SecurityUtils.hasAuthority("loan:update:any:user");
		
		if (!isOwner && !canReadAny) {
			throw new AuthorizationDeniedException("You cannot access this loan");
		}
	}
	
	private List<Book> getBooks(List<Integer> libros) {
	    List<Book> books = bookRepositorio.findByIdLibroIn(libros);

	    if (books.size() != libros.size()) {
	        throw new EntityNotFoundException("One or more books not found");
	    }
	    return books;
	}
	
}