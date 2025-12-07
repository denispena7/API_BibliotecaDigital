package es.library.springboot.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	private String nombreUsuario;
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
	private int tipoUsuario;
	private String iconoUsuario;
	
    @OneToMany(mappedBy = "usuario")
    private List<Rating> puntuaciones;
}