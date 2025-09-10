package com.sca.controller;

import com.sca.dto.BarrilStatusDTO;
import com.sca.dto.BarrilSummaryDTO;
import com.sca.service.BarrilReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Nuevo controlador para exponer endpoints de barriles
@RestController
@RequestMapping("/reports/barriles")
public class BarrilReportController {

    @Autowired
    private BarrilReportService reportService;

    @GetMapping
    public List<BarrilStatusDTO> getBarriles() {
        return reportService.listAllBarrilStatuses();
    }

    @GetMapping("/summary")
    public BarrilSummaryDTO getBarrilSummary() {
        return reportService.summaryBarrilesByEstado();
    }

    @GetMapping("/count")
    public long countBarrilesByEstado(@RequestParam String estado) {
        return reportService.countBarrilesByEstado(estado);
    }

    @GetMapping("/ids")
    public List<Long> getIdsByEstado(@RequestParam String estado) {
        return reportService.findIdsByEstado(estado);
    }

    @GetMapping("/byEstado")
    public List<BarrilStatusDTO> getBarrilesByEstado(@RequestParam String estado) {
        return reportService.findBarrilesByEstado(estado);
    }

    @GetMapping("/total")
    public long getTotalBarriles() {
        return reportService.totalBarriles();
    }

    @GetMapping("/check-threshold")
    public String checkThreshold(@RequestParam int threshold) {
    boolean stockBajo = reportService.isStockBelowThreshold(threshold);
    if (stockBajo) {
        return "⚠️ Stock de barriles por debajo del umbral (" + threshold + ")";
    } else {
        return "✅ Stock suficiente, umbral: " + threshold;
    }
}

}
