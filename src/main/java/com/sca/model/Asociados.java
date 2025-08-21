package com.sca.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
// import javax.persistence.OneToOne;
//import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sca.constantes.ExpresionRegular;
import com.sca.validator.ValidarExpresionesRegulares;


// ESTA ES LA CLASE USUARIO, NO SE LE CAMBIO EL NOMBRE PARA NO ROMPER EL SISTEMA
@Entity
@Table(name="asociado")
@JsonIdentityInfo(
	    generator = ObjectIdGenerators.PropertyGenerator.class,
	    property = "id"
	)
public class Asociados {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	// @OneToOne
	// @JoinColumn(name="id_firma")
    // private Firma id_firma;

	@Column(name="id_firma")
	private Integer id_firma;

	@ValidarExpresionesRegulares(customMessage="El legajo debe tener 3 digitos", expresionRegular = ExpresionRegular.LEGAJO)
	@NotBlank(message = "El legajo no puede estar en blanco")
	@Column(name="legajo")
    private String legajo;

	@NotBlank(message = "El nombre no puede estar en blanco")
	@Column(name="nombre")
	@NotNull
	@ValidarExpresionesRegulares(customMessage = "El nombre no es válido", expresionRegular = ExpresionRegular.NOMBREAPELLIDO)
	private String nombre;
	
	@ValidarExpresionesRegulares(customMessage = "El apellido no es válido", expresionRegular = ExpresionRegular.NOMBREAPELLIDO)
	@NotBlank(message = "El apellido no puede estar en blanco")
	@Column(name="apellido")
	private String apellido;
	
	@Column(name="documento")
	private String documento;

	@Column(name="email")
	private String email;
	
	@Column(name="rol")
	private String rol;

	@Column(name="contrasena")
	private String contrasena;

	@Column(name="activo")
	@NotNull
	private int activo;

	@ManyToMany
    @JoinTable(
        name = "asociado_categoria", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "asociado_id"), // Llave foránea de la tabla 'asociado'
        inverseJoinColumns = @JoinColumn(name = "categoria_id") // Llave foránea de la tabla 'categoria'
    )
    private Set<Categoria> categorias;
	
	@Column(name="telefono")
	@NotNull
	private String telefono;
	
	@OneToMany(mappedBy = "asociado", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<AsociadosCondicion> condiciones = new ArrayList<>();
	
	
    public Asociados() {
    }

	public Asociados(long id, @NotBlank(message = "El nombre no puede estar en blanco") @NotNull String nombre,
			@NotBlank(message = "El apellido no puede estar en blanco") String apellido,
			@NotBlank(message = "El legajo no puede estar en blanco") String legajo, Integer id_firma, String documento,
			Set<Categoria> categorias,@NotNull int activo, @NotNull String telefono) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.legajo = legajo;
		this.id_firma = id_firma;
		this.documento = documento;
		this.categorias = categorias;
		this.activo = activo;
		this.telefono = telefono;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getLegajo() {
		return legajo;
	}

	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}

	// public Firma getId_firma() {
	// 	return id_firma;
	// }

	// public void setId_firma(Firma id_firma) {
	// 	this.id_firma = id_firma;
	// }

	public Integer getId_firma() {
		return id_firma;
	}

	public void setId_firma(Integer id_firma) {
		this.id_firma = id_firma;
	}

	public Set<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<Categoria> categorias) {
		this.categorias = categorias;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Asociados [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", legajo=" + legajo
				+ ", id_firma=" + id_firma + ", categorias=" + categorias + ", activo=" + activo + ", telefono="
				+ telefono + "]";
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public List<AsociadosCondicion> getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(List<AsociadosCondicion> condiciones) {
		this.condiciones = condiciones;
	}	
	
	
}
