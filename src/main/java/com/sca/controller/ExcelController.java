package com.sca.controller;

import com.sca.service.ExcelService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/export")
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    /**
     * Endpoint principal: recibe directamente la lista (array JSON)
     * Ej: [ {"id":1,"nombre":"Lunes"}, {"id":2,"nombre":"Martes"} ]
     */
    @PostMapping(value = "/excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<ByteArrayResource> exportExcel(@RequestBody List<Map<String, Object>> data) throws IOException {
        byte[] bytes = excelService.generateExcelFromList(data);
        ByteArrayResource resource = new ByteArrayResource(bytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=datos.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

    /**
     * Variante práctica: si quieres pasar directamente la respuesta completa
     * del otro servicio (objeto que tiene {status,codigo,descripcion,data: [...]})
     * puedes usar este endpoint: toma el campo "data" y delega al mismo método.
     */
    @PostMapping(value = "/excel/from-response", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<ByteArrayResource> exportExcelFromResponse(@RequestBody Map<String, Object> responseWrapper) throws IOException {
        Object dataObj = responseWrapper.get("data");
        if (!(dataObj instanceof List)) {
            return ResponseEntity.badRequest().build();
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> data = (List<Map<String, Object>>) dataObj;
        return exportExcel(data);
    }
}
