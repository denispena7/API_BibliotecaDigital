package es.library.springboot.models;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="usuarios")
public class User implements UserDetails
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	private String nombreRealUsuario;
	private String apellidosUsuario;
	private String direccionUsuario;
	private String ciudadUsuario;
	private String localidadUsuario;
	private int cpUsuario;
	private int telefonoUsuario;
	private String emailUsuario;
	@Column(length=256)
	private String claveUsuario;
	private String iconoUsuario;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roles_usuarios",
        joinColumns = @JoinColumn(name = "idUsuarioRolFK"),
        inverseJoinColumns = @JoinColumn(name = "idRolUsuarioFK")
    )
    private Set<Rol> roles;
	
    @OneToMany(mappedBy = "usuario")
    private List<Rating> puntuaciones;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
        return roles.stream()
                .flatMap(rol -> rol.getNombreRol().getAuthorities().stream())
                .collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {return claveUsuario;}
	@Override
	public String getUsername() {return emailUsuario;}
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}