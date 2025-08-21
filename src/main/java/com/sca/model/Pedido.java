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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaPedido;

    @NotNull(message = "La fecha de entrega es obligatoria")
    @Column(name = "fecha_entrega", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaEntrega;
    
    @NotEmpty(message = "La dirección de entrega es obligatoria")
    @Column(name="direccionEntrega", nullable = false)
    private String direccionEntrega;
    
    @NotEmpty(message = "El estado es obligatorio")
    @Column(name="estado", nullable = false)
    private String estado;
    
    @NotNull(message = "El campo envío es obligatorio")
    @Column(name="envio", nullable = false)
    private Boolean envio;
    
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

    public Pedido(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getEnvio() {
        return envio;
    }

    public void setEnvio(Boolean envio) {
        this.envio = envio;
    }

    public Set<Accesorio> getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(Set<Accesorio> accesorios) {
        this.accesorios = accesorios;
    }

    public Set<Cerveza> getCervezas() {
        return cervezas;
    }

    public void setCervezas(Set<Cerveza> cervezas) {
        this.cervezas = cervezas;
    }

    public Set<Barril> getBarriles() {
        return barriles;
    }

    public void setBarriles(Set<Barril> barriles) {
        this.barriles = barriles;
    }

    public Asociados getUsuario() {
        return usuario;
    }

    public void setUsuario(Asociados usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getTotalGral() {
        return totalGral;
    }

    public void setTotalGral(Double totalGral) {
        this.totalGral = totalGral;
    }
}
