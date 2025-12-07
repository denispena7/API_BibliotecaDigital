package es.library.springboot.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="prestamo")
public class Loan 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPrestamo;
	private LocalDate fechaInicio;
	private LocalDate fechaDevolucionEsperada;
	private LocalDate fechaDevolucionReal;
	private String estado;
	
	@OneToOne
	@JoinColumn(name="idUsuarioFK", foreignKey = @ForeignKey(name="fk_prestamos_usuarios"))
	private User usuario;
	
	@ManyToMany
	@JoinTable(
	    name = "prestamos_libros",
	    joinColumns = @JoinColumn(name = "idPrestamoFK"),
	    inverseJoinColumns = @JoinColumn(name = "idLibroPrestFK")
	)
	private List<Book> libros;
}