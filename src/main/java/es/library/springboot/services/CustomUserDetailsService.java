package es.library.springboot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.library.springboot.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService 
{
    private final UserRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) //  throws UsernameNotFoundException 
    {
        return usuarioRepository.findByEmailUsuario(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email));
    }
}