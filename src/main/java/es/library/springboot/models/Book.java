package es.library.springboot.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="libros")
public class Book 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idLibro;
	@Column(length=256)
	private String tituloLibro;
	
	@Column(length=4)
	private int anioPublicacion;
	
	@Lob
	private String sinopsisLibro;
	@Column(length=45)
	private String estadoLibro;
	@Column(length=256)
	private String portadaLibro;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idAutorFK",
    nullable=false,
    foreignKey=@ForeignKey(name="fk_libros_autores"))
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
    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable(
//    		name="prestamos_libros",
//    		joinColumns=@JoinColumn(name="idLibroPrestFK"),
//    		inverseJoinColumns=@JoinColumn(name="idPrestamoFK"))
    @ManyToMany(mappedBy = "libros")
    private List<Loan> prestamos;
   // private Loan prestamo;
}