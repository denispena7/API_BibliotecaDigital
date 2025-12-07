package es.library.springboot.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="libros")
public class Book 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLibro;
	
	private String tituloLibro;
	
	@Column(length=4)
	private int anioPublicacion;
	
	@Lob
	private String sinopsisLibro;
	
	private String estadoLibro;
	private String portadaLibro;
	
	@ManyToOne
	@JoinColumn(name = "idAutorFK", foreignKey = @ForeignKey(name="fk_libros_autores") )
	private Author autor;
	
	@ManyToMany
	@JoinTable(
			name="clasificaciones", 
			joinColumns=@JoinColumn(name="idLibroCatFK"),
			inverseJoinColumns=@JoinColumn(name="idCategoriaFK")
	)
	private List<Category> categorias;
	
    @OneToMany(mappedBy = "libro")
    private List<Rating> puntuaciones;
    
    @ManyToOne
    @JoinTable(
    		name="prestamos_libros",
    		joinColumns=@JoinColumn(name="idLibroPrestFK"),
    		inverseJoinColumns=@JoinColumn(name="idPrestamoFK"))
    private Loan prestamo;
}