package es.library.springboot.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.library.springboot.DTOs.requests.UpdateEmailRequestDTO;
import es.library.springboot.DTOs.requests.UpdatePasswordRequestDTO;
import es.library.springboot.DTOs.requests.UserCreateAsAdminDTO;
import es.library.springboot.DTOs.requests.UserUpdateDTO;
import es.library.springboot.DTOs.responses.PageResponse;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.DTOs.responses.UserSelfReadDTO;
import es.library.springboot.config.Role;
import es.library.springboot.config.SecurityUtils;
import es.library.springboot.exceptions.EntityNotFoundException;
import es.library.springboot.exceptions.ValidateException;
import es.library.springboot.mapper.PageMapper;
import es.library.springboot.mapper.UserMapper;
import es.library.springboot.models.Rol;
import es.library.springboot.models.User;
import es.library.springboot.repositories.RolRepository;
import es.library.springboot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService 
{
	private final UserRepository userRepositorio;
	private final RolRepository rolRepositorio;
	private final UserMapper userMapper;
	private final PageMapper pageMapper;
	
	Pageable pageable;
	
	private final FileStorageService fileStorageService;
	private final StorageProperties storageProperties;
	
	private final PasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public PageResponse<UserResponseDTO> obtenerUsuarios(int page, int size)
	{
		pageable = PageableService.getPageable(page, size, "nombreRealUsuario");
		
		Page<User> pageUsers = userRepositorio.findAll(pageable);
		
		return pageMapper.toPageResponse(pageUsers, userMapper::toUserResponseDTO);
	}
	
	@Transactional(readOnly = true)
	public UserResponseDTO obtenerUsuario(Long id)
	{
	    return userRepositorio.findById(id)
	            .map(userMapper::toUserResponseDTO)
	            .orElseThrow(() ->
	                new EntityNotFoundException("User not found"));
	}
	
	@Transactional(readOnly = true)
	public UserSelfReadDTO obtenerUsuarioActual() 
	{		
		User user = SecurityUtils.getCurrentUser();
	    return buildUserResponse(user);
	}
	
	@Transactional
	public UserResponseDTO actualizarRol(Long id, UserCreateAsAdminDTO user)
	{
		    // 1. Cargar usuario existente
		    User userEntity = userRepositorio.findByIdUsuario(id)
		            .orElseThrow(() -> new EntityNotFoundException("User not found"));

		    // 2. Mapear roles
		    Set<Rol> roles = user.roles().stream()
		            .map(roleStr -> {
		                Role roleEnum;
		                try {
		                    roleEnum = Role.valueOf(roleStr);
		                } catch (IllegalArgumentException e) {
		                    throw new ValidateException("Invalid role: " + roleStr);
		                }

		                return rolRepositorio.findByNombreRol(roleEnum)
		                        .orElseThrow(() -> new ValidateException("Role not found: " + roleStr));
		            })
		            .collect(Collectors.toSet());

		    // 3. Asignar roles
		    userEntity.setRoles(roles);

		    // 4. Guardar
		    User saved = userRepositorio.save(userEntity);

		    return userMapper.toUserResponseDTO(saved);
	}
	
	@Transactional
	public UserResponseDTO actualizarEmail(Long id, UpdateEmailRequestDTO email) 
	{
		User userEntity = userRepositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
	    
	    autorizar(userEntity);
	    
	    userEntity.setEmailUsuario(email.email());
	    
	    return userMapper.toUserResponseDTO(userRepositorio.save(userEntity));
	}
	
	@Transactional
	public UserResponseDTO actualizarIcono(Long id, MultipartFile file) 
	{
		User usuario = userRepositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
		
		autorizar(usuario);
		
		updateUserImage(usuario, file);
		
		return userMapper.toUserResponseDTO(usuario);
	}

	@Transactional
	public UserResponseDTO actualizarPassword(Long id, UpdatePasswordRequestDTO dto) 
	{
		User userEntity = userRepositorio.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found"));
	    
	    autorizar(userEntity);
	    
	    if (!passwordEncoder.matches(dto.currentPassword(), userEntity.getPassword())) {
	        throw new ValidateException("Current password is incorrect");
	    }
	    
	    userEntity.setClaveUsuario(passwordEncoder.encode(dto.password()));
	    
	    return userMapper.toUserResponseDTO(userRepositorio.save(userEntity));
	}

	@Transactional
	public UserResponseDTO actualizarInfoUsuario(Long id, UserUpdateDTO usuario)
	{
	    User userEntity = userRepositorio.findById(id)
	            .orElseThrow(() -> new ValidateException("User not found"));
	    
	    autorizar(userEntity);
	    
	    userEntity.setNombreRealUsuario(usuario.nombreRealUsuario());
	    userEntity.setApellidosUsuario(usuario.apellidosUsuario());
	    userEntity.setDireccionUsuario(usuario.direccionUsuario());
	    userEntity.setCiudadUsuario(usuario.ciudadUsuario());
	    userEntity.setLocalidadUsuario(usuario.localidadUsuario());
	    userEntity.setCpUsuario(usuario.cpUsuario());
	    userEntity.setTelefonoUsuario(usuario.telefonoUsuario());
	    
	    User updated = userRepositorio.save(userEntity);
	    
		return userMapper.toUserResponseDTO(updated);
	}

	@Transactional
	public void eliminarUsuario(Long id) 
	{
	    User userEntity = userRepositorio.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("User not found"));
	    
	    autorizar(userEntity);
		
	    userRepositorio.delete(userEntity);
	    
	    fileStorageService.deleteFileIfExists(userEntity.getIconoUsuario(), storageProperties.getUserPath());
	}
	
	private void autorizar(User user) 
	{
		User principal = SecurityUtils.getCurrentUser();

	    boolean isOwner = user.getIdUsuario()
	            .equals(principal.getIdUsuario());

	    boolean canReadAny = SecurityUtils.hasAuthority("user:update:any");

	    if (!isOwner && !canReadAny) {
	        throw new AuthorizationDeniedException("You cannot modify this user");
	    }
	}
	
	private void updateUserImage(User user, MultipartFile file) 
	{
	    if (file == null || file.isEmpty()) return;

	    fileStorageService.deleteFileIfExists(
	            user.getIconoUsuario(),
	            storageProperties.getUserPath()
	    );

	    String filename = fileStorageService.saveFile(
	            file,
	            storageProperties.getUserPath()
	    );

	    user.setIconoUsuario(filename);
	}
	
	private UserSelfReadDTO buildUserResponse(User user) 
	{
	    UserSelfReadDTO dto = userMapper.toUserDTO(user);

	    return new UserSelfReadDTO(
	    		dto.nombreRealUsuario(),
	    		dto.apellidosUsuario(),
	    		dto.direccionUsuario(),
	    		dto.ciudadUsuario(),
	    		dto.localidadUsuario(),
	    		dto.cpUsuario(),
	    		dto.telefonoUsuario(),
	    		dto.emailUsuario(),
	        fileStorageService.buildPublicUrl(user.getIconoUsuario(),storageProperties.getUserPath()));
	}
}