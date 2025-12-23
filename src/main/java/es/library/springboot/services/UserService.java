package es.library.springboot.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.library.springboot.DTOs.PageResponse;
import es.library.springboot.DTOs.UserDTO;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.mapper.UserMapper;
import es.library.springboot.models.User;
import es.library.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService 
{
	private final UserRepository userRepositorio;
	private final UserMapper userMapper;
	private final PageMapper pageMapper;
	
	Pageable pageable;
	
	@Transactional(readOnly = true)
	public PageResponse<UserDTO> obtenerUsuarios(int page, int size)
	{
		pageable = PageableService.getPageable(page, size, "nombreUsuario");
		
		Page<User> pageUsers = userRepositorio.findAll(pageable);
		
		return pageMapper.toPageResponse(pageUsers, userMapper::toUserDTO);
	}
	
	@Transactional(readOnly = true)
	public UserDTO obtenerUsuario(Long id)
	{
	    return userRepositorio.findById(id)
	            .map(userMapper::toUserDTO)
	            .orElseThrow(() ->
	                new EntityNotFoundException("User not found"));
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