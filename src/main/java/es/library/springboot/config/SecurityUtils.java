package es.library.springboot.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import es.library.springboot.models.User;

public class SecurityUtils
{
    public static User getCurrentUser() 
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
		if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) 
		{
			throw new AccessDeniedException("User not authenticated");
		}
		
        return (User) auth.getPrincipal();
    }

    public static boolean hasAuthority(String authority) 
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }
}
