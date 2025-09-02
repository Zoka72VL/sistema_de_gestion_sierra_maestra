package com.sca.service.impl;

import com.sca.service.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Service
public class ExcelServiceImpl extends ResponseEntityExceptionHandler implements ExcelService {

    @Override
    public byte[] generateExcelFromList(List<Map<String, Object>> data) throws IOException {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("La lista 'data' está vacía o es nula");
        }

        // Recolectar todos los encabezados en orden
        LinkedHashSet<String> headerSet = new LinkedHashSet<>();
        for (Map<String, Object> row : data) {
            if (row != null) headerSet.addAll(row.keySet());
        }
        List<String> headers = new ArrayList<>(headerSet);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Datos");

            // Fila de encabezado
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
            }

            // Filas de datos
            for (int r = 0; r < data.size(); r++) {
                Row excelRow = sheet.createRow(r + 1);
                Map<String, Object> mapRow = data.get(r);
                for (int c = 0; c < headers.size(); c++) {
                    Object value = mapRow != null ? mapRow.get(headers.get(c)) : null;
                    Cell cell = excelRow.createCell(c);
                    if (value != null) {
                        // Tipar algunos tipos básicos para mejor compatibilidad en Excel
                        if (value instanceof Number) {
                            cell.setCellValue(((Number) value).doubleValue());
                        } else if (value instanceof Boolean) {
                            cell.setCellValue((Boolean) value);
                        } else if (value instanceof Date) {
                            CellStyle dateStyle = workbook.createCellStyle();
                            CreationHelper createHelper = workbook.getCreationHelper();
                            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));
                            cell.setCellStyle(dateStyle);
                            cell.setCellValue((Date) value);
                        } else {
                            cell.setCellValue(value.toString());
                        }
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

            // Auto-ajustar columnas (simple, puede costar CPU con muchas columnas)
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
