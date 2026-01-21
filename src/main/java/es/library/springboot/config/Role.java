package es.library.springboot.config;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role 
{
    ADMIN(Set.of(Permission.values())),

    USER(Set.of(
    		Permission.AUTHOR_READ,
            Permission.BOOK_READ,
            Permission.CATEGORY_READ,
            Permission.LOAN_READ_SELF,
            Permission.LOAN_CREATE_SELF,
            Permission.LOAN_RETURN_SELF,
            Permission.AVG_RATING_READ,
            Permission.RATING_READ_SELF,
            Permission.RATING_CREATE_SELF,
            Permission.RATING_UPDATE_SELF,
            Permission.USER_CREATE_LOW_LEVEL,
            Permission.USER_UPDATE_SELF,
            Permission.USER_DELETE_SELF
    )),

    EMPLOYEE(Set.of(
    		Permission.AUTHOR_READ,
    		Permission.AUTHOR_CREATE,
    		Permission.AUTHOR_UPDATE,
    		Permission.BOOK_READ,
            Permission.BOOK_CREATE,
            Permission.BOOK_UPDATE,
            Permission.CATEGORY_READ,
            Permission.CATEGORY_CREATE,
            Permission.CATEGORY_UPDATE,
            Permission.LOAN_READ_ANY_USER,
            Permission.LOAN_CREATE_ANY_USER,
            Permission.LOAN_RETURN_ANY_USER,
            Permission.AVG_RATING_READ,
            Permission.USER_READ_ANY,
            Permission.USER_CREATE_LOW_LEVEL,
            Permission.USER_UPDATE_SELF
    ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() 
    {
        Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
        
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
