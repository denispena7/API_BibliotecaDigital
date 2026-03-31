package es.library.springboot.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.library.springboot.DTOs.requests.LoanRequestDTO;
import es.library.springboot.DTOs.responses.LoanResponseDTO;
import es.library.springboot.models.Loan;

@Mapper(componentModel = "spring")
public interface LoanMapper 
{
	@Mapping(source = "idPrestamo",  target = "idPrestamo")
	@Mapping(source = "usuario.emailUsuario", target = "emailUsuario")
	LoanResponseDTO toLoanDTO(Loan loan);
	
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "fechaInicio", source = "fechaInicio")
    @Mapping(target = "fechaDevolucionEsperada", source = "fechaDevolucionEsperada")
    @Mapping(target = "fechaDevolucionReal", source = "fechaDevolucionReal")
    @Mapping(target = "estado", source = "estado")
	Loan toLoanEntity(LoanRequestDTO loanDTO);
}