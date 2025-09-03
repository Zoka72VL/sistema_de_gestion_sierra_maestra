package com.sca.controller;

import com.sca.service.CsvService;

import io.swagger.annotations.Api;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "CsvExport")
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    /**
     * Recibe un wrapper JSON con clave "data" que debe ser un array de objetos.
     * Ejemplo:
     * {
     *   "status": 200,
     *   "data": [
     *     { "id": 1, "nombre": "Lunes" },
     *     { "id": 2, "nombre": "Martes" }
     *   ]
     * }
     */
    @PostMapping(
        value = "/csv",
        produces = "text/csv; charset=UTF-8"
    )
    public ResponseEntity<ByteArrayResource> exportCsvFromResponse(
            @RequestBody Map<String, Object> wrapper) {

        Object obj = wrapper.get("data");
        if (!(obj instanceof List)) {
            return ResponseEntity
                .badRequest()
                .body(null);
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> data = (List<Map<String, Object>>) obj;

        byte[] csvBytes = csvService.generateCsv(data);
        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=datos.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(csvBytes.length)
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(resource);
    }
}
