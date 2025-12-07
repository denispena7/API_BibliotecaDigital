package es.library.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.library.springboot.DTOs.UserDTO;
import es.library.springboot.mapper.UserMapper;
import es.library.springboot.models.User;
import es.library.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService 
{
	private final UserRepository userRepositorio;
	private final UserMapper userMapper;
	
	public List<UserDTO> obtenerUsuarios()
	{
		return userMapper.toUserDTOList(
				userRepositorio.findAll()
		);
	}
	
	public Optional<UserDTO> obtenerUsuario(String name)
	{
		return userRepositorio.findByNombreUsuario(name)
				.map(userMapper::toUserDTO);
	}
	
	public User guardarUsuario(User user)
	{
		return userRepositorio.save(user);
	}
	
    public User actualizar(Long id, User userDetalles) 
    {
        return userRepositorio.findById(id)
            .map(user -> {
            	user.setNombreUsuario(userDetalles.getNombreUsuario());
            	user.setEmailUsuario(userDetalles.getEmailUsuario());
            	user.setClaveUsuario(userDetalles.getClaveUsuario());
            	user.setTipoUsuario(userDetalles.getTipoUsuario());
                return userRepositorio.save(user);
            })
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }
}