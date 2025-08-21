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
@Getter
@Setter
@EqualsAndHashCode
@ToString
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
}


   
  