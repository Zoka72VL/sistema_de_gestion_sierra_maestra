package com.sca.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExcelService {
    /**
     * Genera bytes de un archivo Excel (.xlsx) a partir de una lista de filas,
     * donde cada fila es un Map clave->valor (preserva el orden de claves
     * si usas LinkedHashMap al crear el JSON).
     *
     * @param data lista de mapas (cada mapa representa una fila)
     * @return arreglo de bytes con el .xlsx
     * @throws IOException si falla la escritura
     */
    byte[] generateExcelFromList(List<Map<String, Object>> data) throws IOException;
}
