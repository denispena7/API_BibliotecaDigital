package es.library.springboot.services;

import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.library.springboot.config.Role;
import es.library.springboot.config.securityjwt.JwtService;
import es.library.springboot.DTOs.requests.LoginRequestDTO;
import es.library.springboot.DTOs.requests.UserRegisterDTO;
import es.library.springboot.DTOs.responses.AuthResponseDTO;
import es.library.springboot.DTOs.responses.UserResponseDTO;
import es.library.springboot.exceptions.ValidateException;
import es.library.springboot.mapper.UserMapper;
import es.library.springboot.models.Rol;
import es.library.springboot.models.User;
import es.library.springboot.repositories.RolRepository;
import es.library.springboot.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService 
{
    private final UserRepository usuarioRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final RolRepository rolRepositorio;

    public AuthResponseDTO login(LoginRequestDTO request) 
    {
    	User usuario = usuarioRepository.findByEmailUsuario(request.getEmail())
    			.orElseThrow(() -> new UsernameNotFoundException("User not registered"));
    	
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(usuario);

        return AuthResponseDTO.builder()
                .id(usuario.getIdUsuario())
                .email(usuario.getEmailUsuario())
                .token(jwtToken)
                .build();
    }

	public UserResponseDTO register(@Valid UserRegisterDTO dto) 
	{
	    if (usuarioRepository.existsByEmailUsuario(dto.emailUsuario())) {
	        throw new ValidateException("User already exists");
	    }
    	
	    User userEntity = userMapper.toUserEntityFromUR(dto);
	    
	    userEntity.setClaveUsuario(passwordEncoder.encode(dto.password()));
	    
	    // 3. Asignar SIEMPRE rol USER
	    Rol userRole = rolRepositorio.findByNombreRol(Role.USER)
	            .orElseThrow(() -> new ValidateException("Default role USER not found"));

	    userEntity.setRoles(Set.of(userRole));

	    User saved = usuarioRepository.save(userEntity);
	    
	    return userMapper.toUserResponseDTO(saved);
	}
}