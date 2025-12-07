package es.library.springboot.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import es.library.springboot.DTOs.UserDTO;
import es.library.springboot.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper 
{
	UserDTO toUserDTO(User user);
	List<UserDTO> toUserDTOList(List<User> usuarios);
}