package com.sca.dto;

// Nuevo DTO para representar un barril individual
public class BarrilStatusDTO {
    private Long id;
    private String estado;
    private Integer litros;

    public BarrilStatusDTO() {}

    public BarrilStatusDTO(Long id, String estado, Integer litros) {
        this.id = id;
        this.estado = estado;
        this.litros = litros;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Integer getLitros() { return litros; }
    public void setLitros(Integer litros) { this.litros = litros; }
}
