package com.sca.model;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "El ID no puede ser nulo")
    private Long id;


    @NotNull(message = "La fecha de pedido es obligatoria")
    @Column(name = "fecha_pedido", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaPedido;

    @NotNull(message = "La fecha de entrega es obligatoria")
    @Column(name = "fecha_entrega", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fechaEntrega;
    
    @NotNull(message = "El campo envío es obligatorio")
    @Column(name="envio", nullable = false)
    private boolean envio;

    // 👇 dirección de entrega solo se valida en el servicio
    @Column(name="direccionEntrega")
    private String direccionEntrega;

    
    @NotEmpty(message = "El estado es obligatorio")
    @Column(name="estado", nullable = false)
    private String estado;
    
    @ManyToMany
    @JoinTable(
        name = "pedido_accesorio",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "accesorio_id")
    )
    @Column(name="accesorios")
    private Set<Accesorio> accesorios = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "pedido_cerveza",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "cerveza_id")
    )
    @Column(name="cervezas")
    private Set<Cerveza> cervezas = new HashSet<>();
    
    @OneToMany
    @JoinColumn(name = "pedido_id")
    @Column(name="barriles")
    private Set<Barril> barriles = new HashSet<>();
    
    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Asociados usuario;
    
    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @NotNull(message = "El total general es obligatorio")
    @PositiveOrZero(message = "El total general no puede ser negativo")
    @Column(name="totalGral", nullable = false)
    private Double totalGral;

    @NotNull(message = "El estado de pago es obligatorio")
    @Column(name = "estado_pago", nullable = false)
    private String estadoPago = "Pendiente"; // valor por defecto

    @Column(name = "nota", columnDefinition = "TEXT")
    private String nota;

    public String getNota() {
    return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }


}
