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

@Entity
@Table(name="cervezas")
public class Cerveza {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="nombre",nullable = false, length = 50)
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

	@Column(name="estado")
	private Boolean estado;

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
			String descripcion, Double precioPorLitro, Boolean estado) {
		this.id = id;
		this.nombreCerveza = nombreCerveza;
		this.tipoCerveza = tipoCerveza;
		this.gradoAlcoholico = gradoAlcoholico;
		this.amargorIbu = amargorIbu;
		this.descripcion = descripcion;
		this.precioPorLitro = precioPorLitro;
		this.estado = estado;
	}

	public Double getAmargorIbu() {
		return amargorIbu;
	}

	public void setAmargorIbu(Double amargorIbu) {
		this.amargorIbu = amargorIbu;
	}

	
	
	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCerveza() {
		return nombreCerveza;
	}

	public void setNombreCerveza(String nombreCerveza) {
		this.nombreCerveza = nombreCerveza;
	}

	public String getTipoCerveza() {
		return tipoCerveza;
	}

	public void setTipoCerveza(String tipoCerveza) {
		this.tipoCerveza = tipoCerveza;
	}

	public Double getGradoAlcoholico() {
		return gradoAlcoholico;
	}

	public void setGradoAlcoholico(Double gradoAlcoholico) {
		this.gradoAlcoholico = gradoAlcoholico;
	}

	public Double getPrecioPorLitro() {
		return precioPorLitro;
	}

	public void setPrecioPorLitro(Double precioPorLitro) {
		this.precioPorLitro = precioPorLitro;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Cerveza [id=" + id + ", nombreCerveza=" + nombreCerveza + ", tipoCerveza=" + tipoCerveza
				+ ", gradoAlcoholico=" + gradoAlcoholico + ", amargorIbu=" + amargorIbu + ", descripcion=" + descripcion
				+ ", precioPorLitro=" + precioPorLitro + ", estado=" + estado + "]";
	}
}
