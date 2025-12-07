package es.library.springboot.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.library.springboot.DTOs.LoanDTO;
import es.library.springboot.models.Loan;

@Mapper(componentModel = "spring")
public interface LoanMapper 
{
	@Mapping(source = "usuario.nombreUsuario", target = "nombreUsuario")
	LoanDTO toLoanDTO(Loan loan);
	
	List<LoanDTO> toLoanDTOList(List<Loan> loans);
}