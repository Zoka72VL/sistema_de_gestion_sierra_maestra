package com.sca.model;
import java.util.Date;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
// import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
// import javax.persistence.Lob;
// import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
// import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name="pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(name = "fecha_pedido", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaPedido;

    @Column(name = "fecha_entrega", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaEntrega;
    
    @Column(name="direccionEntrega")
    private String direccionEntrega;
    
    @Column(name="estado")
    private String estado;
    
    @Column(name="envio")
    private Boolean envio;
    
    @ManyToMany
    @JoinTable(
      name = "pedido_accesorio",
      joinColumns = @JoinColumn(name = "pedido_id"),
      inverseJoinColumns = @JoinColumn(name = "accesorio_id")
    )
    @Column(name="accesorios")
    private Set<Accesorio> accesorios;
    
    @ManyToMany
    @JoinTable(
      name = "pedido_cerveza",
      joinColumns = @JoinColumn(name = "pedido_id"),
      inverseJoinColumns = @JoinColumn(name = "cerveza_id")
    )
    @Column(name="cervezas")
    private Set<Cerveza> cervezas;
    
    @OneToMany
    @JoinColumn(name = "pedido_id")
    @Column(name="barriles")
    private Set<Barril> barriles;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Asociados usuario;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @Column(name="totalGral")
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
