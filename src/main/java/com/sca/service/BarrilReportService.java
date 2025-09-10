package com.sca.service;

import com.sca.model.Barril;
import com.sca.dto.BarrilStatusDTO;
import com.sca.dto.BarrilSummaryDTO;
import com.sca.repository.BarrilRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

// Nuevo servicio para manejar reportes y conteos de barriles
@Service
public class BarrilReportService {

    @Autowired
    private BarrilRepository barrilRepo;

    // Lista todos los barriles con id, estado y litros
    public List<BarrilStatusDTO> listAllBarrilStatuses() {
        List<Barril> all = barrilRepo.findAll();
        return all.stream()
                .map(b -> new BarrilStatusDTO(b.getId(), b.getEstado(), b.getLitros()))
                .collect(Collectors.toList());
    }

    // Resumen: conteos por estado y total
    public BarrilSummaryDTO summaryBarrilesByEstado() {
        List<Barril> all = barrilRepo.findAll();
        Map<String, Long> counts = all.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getEstado() == null ? "UNKNOWN" : b.getEstado(),
                        Collectors.counting()
                ));
        long total = all.size();
        return new BarrilSummaryDTO(counts, total);
    }

    // Conteo rápido por estado (ej: "Disponible")
    public long countBarrilesByEstado(String estado) {
        if (estado == null) return 0;
        String e = estado.trim().toLowerCase();
        return barrilRepo.findAll().stream()
                .filter(b -> b.getEstado() != null && b.getEstado().trim().toLowerCase().equals(e))
                .count();
    }

    // Lista ids por estado
    public List<Long> findIdsByEstado(String estado) {
        if (estado == null) return Collections.emptyList();
        String e = estado.trim().toLowerCase();
        return barrilRepo.findAll().stream()
                .filter(b -> b.getEstado() != null && b.getEstado().trim().toLowerCase().equals(e))
                .map(Barril::getId)
                .collect(Collectors.toList());
    }

    // Lista detallada de barriles por estado
    public List<BarrilStatusDTO> findBarrilesByEstado(String estado) {
        if (estado == null) return Collections.emptyList();
        String e = estado.trim().toLowerCase();
        return barrilRepo.findAll().stream()
                .filter(b -> b.getEstado() != null && b.getEstado().trim().toLowerCase().equals(e))
                .map(b -> new BarrilStatusDTO(b.getId(), b.getEstado(), b.getLitros()))
                .collect(Collectors.toList());
    }

    // Total de barriles en la base
    public long totalBarriles() {
        return barrilRepo.count();
    }

    // Chequeo de stock bajo según umbral del usuario
    public boolean isStockBelowThreshold(int threshold) {
        long disponibles = countBarrilesByEstado("Disponible");
        return disponibles < threshold;
    }

    // Método para notificación automática (log por ahora)
    public void checkAndNotify(int threshold) {
        if (isStockBelowThreshold(threshold)) {
            System.out.println("⚠️ Stock de barriles por debajo del umbral: " + threshold);
        }
    }
}
