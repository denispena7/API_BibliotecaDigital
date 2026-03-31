package es.library.springboot.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.library.springboot.DTOs.requests.UserCreateAsAdminDTO;
import es.library.springboot.DTOs.requests.UserRegisterDTO;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.DTOs.responses.UserSelfReadDTO;
import es.library.springboot.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper 
{
	UserSelfReadDTO toUserDTO(User user);
	
	UserRegisterDTO toUserRegisterDTO(User user);
	
	UserResponseDTO toUserResponseDTO(User user);
	
    @BeanMapping(ignoreByDefault = true)
	User toUserEntityFromUCAdmin(UserCreateAsAdminDTO userAdmin);
	
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "nombreRealUsuario", source = "nombreRealUsuario")
    @Mapping(target = "apellidosUsuario", source = "apellidosUsuario")
    @Mapping(target = "emailUsuario", source = "emailUsuario")
	User toUserEntityFromUR(UserRegisterDTO dto);
	
	List<UserSelfReadDTO> toUserDTOList(List<User> usuarios);
}