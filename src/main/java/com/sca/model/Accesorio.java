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
@Table(name = "accesorio")
public class Accesorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "notas")
    private String notas; 
    
    // Constructores
    public Accesorio(Long id, String nombre, String estado, String notas) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.notas = notas;
    }

    public Accesorio() {
    }

    public Accesorio(String nombre, String estado, String notas) {
        this.nombre = nombre;
        this.estado = estado;
        this.notas = notas;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }


}


   
  