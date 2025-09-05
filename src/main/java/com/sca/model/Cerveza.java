package com.sca.model;
// import java.util.Date;
// import java.util.Set;
// import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.Lob;
import javax.persistence.Table;
// import javax.persistence.ManyToMany;
// import javax.persistence.OneToMany;
// import javax.persistence.OneToOne;
// import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;
import com.sca.constantes.ExpresionRegular;
import com.sca.validator.ValidarExpresionesRegulares;
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name="cervezas")
public class Cerveza {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="nombre",nullable = false, length = 50)
	@NotEmpty(message = "El nombre no puede estar vacio")
	private String nombreCerveza;

	@Column(name="tipo",nullable = false, length = 30)
	private String tipoCerveza;

	@Column(name="grado_alcoholico", nullable=false)
	private Double gradoAlcoholico;

	@Column(name="amargor_IBU", nullable=false)
	private Double amargorIbu;

	@Column(name="descripcion", nullable=true)
	private String descripcion;

	@Column(name="precioLitro", nullable = true)
	private Double precioPorLitro;

    @NotEmpty(message = "El estado es obligatorio")
    @Column(name = "estado", nullable = false)
    @ValidarExpresionesRegulares(customMessage = "El estado no es válido", expresionRegular = ExpresionRegular.CERVEZA_ESTADO)
    private String estado;

	// @OneToMany(mappedBy = "cerveza")
    // private Set<Lote> lote;
	
	public Cerveza(){}

	public Cerveza(Long id, String nombreCerveza, String tipoCerveza, Double gradoAlcoholico, Double precioPorLitro) {
		this.id = id;
		this.nombreCerveza = nombreCerveza;
		this.tipoCerveza = tipoCerveza;
		this.gradoAlcoholico = gradoAlcoholico;
		this.precioPorLitro = precioPorLitro;
	}

	public Cerveza(String nombreCerveza, String tipoCerveza, Double gradoAlcoholico, Double precioPorLitro) {
		this.nombreCerveza = nombreCerveza;
		this.tipoCerveza = tipoCerveza;
		this.gradoAlcoholico = gradoAlcoholico;
		this.precioPorLitro = precioPorLitro;
	}

	public Cerveza(Long id, String nombreCerveza, String tipoCerveza, Double gradoAlcoholico, String descripcion,
			Double precioPorLitro) {
		this.id = id;
		this.nombreCerveza = nombreCerveza;
		this.tipoCerveza = tipoCerveza;
		this.gradoAlcoholico = gradoAlcoholico;
		this.descripcion = descripcion;
		this.precioPorLitro = precioPorLitro;
	}

	public Cerveza(Long id, String nombreCerveza, String tipoCerveza, Double gradoAlcoholico, Double amargorIbu,
			String descripcion, Double precioPorLitro, String estado) {
		this.id = id;
		this.nombreCerveza = nombreCerveza;
		this.tipoCerveza = tipoCerveza;
		this.gradoAlcoholico = gradoAlcoholico;
		this.amargorIbu = amargorIbu;
		this.descripcion = descripcion;
		this.precioPorLitro = precioPorLitro;
		this.estado = estado;
	}
}
