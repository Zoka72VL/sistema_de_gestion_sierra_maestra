package com.sca.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "asociado_condicion")
@JsonIdentityInfo(
	    generator = ObjectIdGenerators.PropertyGenerator.class,
	    property = "id"
	)
public class AsociadosCondicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "asociado_id", nullable = false)
    private Asociados asociado;

    @ManyToOne
    @JoinColumn(name = "condicion_id", nullable = false)
    @NotNull
    private Condicion condicion;
    
    @Column(name="fecha")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fecha;

    public AsociadosCondicion() {
    }

    public AsociadosCondicion(long id, @NotNull Asociados asociado, @NotNull Condicion condicion, Date fecha) {
		super();
		this.id = id;
		this.asociado = asociado;
		this.condicion = condicion;
		this.fecha = fecha;
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Asociados getAsociado() {
        return asociado;
    }

    public void setAsociado(Asociados asociado) {
        this.asociado = asociado;
    }

    public Condicion getCondicion() {
        return condicion;
    }

    public void setCondicion(Condicion condicion) {
        this.condicion = condicion;
    }

    @Override
    public String toString() {
        return "AsociadoCondicion [id=" + id + ", asociado=" + asociado + ", condicion=" + condicion + "]";
    }

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
    
    
}
