package com.sca.dto;

import java.util.Map;

// Nuevo DTO para resumen de barriles
public class BarrilSummaryDTO {
    private Map<String, Long> countsByEstado; // conteos por estado
    private long total;                       // total de barriles

    public BarrilSummaryDTO() {}

    public BarrilSummaryDTO(Map<String, Long> countsByEstado, long total) {
        this.countsByEstado = countsByEstado;
        this.total = total;
    }

    public Map<String, Long> getCountsByEstado() { return countsByEstado; }
    public void setCountsByEstado(Map<String, Long> countsByEstado) { this.countsByEstado = countsByEstado; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
}
