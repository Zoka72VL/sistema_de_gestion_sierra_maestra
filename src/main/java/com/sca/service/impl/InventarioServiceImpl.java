package com.sca.service.impl;

import com.sca.service.InventarioService;
import com.sca.service.LoteService;
import com.sca.service.MaduradorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sca.model.Respuesta;
import com.sca.service.AccesorioService;
import com.sca.service.BarrilService;
import com.sca.service.CervezaService;
import com.sca.service.CsvService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InventarioServiceImpl extends ResponseEntityExceptionHandler implements InventarioService{

    private final LoteService lotesService;
    private final BarrilService barrilService;
    private final CervezaService cervezaService;
    private final AccesorioService accesorioService;
    private final MaduradorService maduradorService;
    private final CsvService csvService;

    public InventarioServiceImpl(LoteService lotesService,BarrilService barrilService,CervezaService cervezaService,AccesorioService accesorioService,MaduradorService maduradorService,CsvService csvService) {
        this.lotesService = lotesService;
        this.barrilService = barrilService;
        this.cervezaService = cervezaService;
        this.accesorioService = accesorioService;
        this.maduradorService = maduradorService;
        this.csvService = csvService;
    }
    
    private String byteToString(byte[] data){
        return new String(data, StandardCharsets.UTF_8);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> toMapList(Object rawData) {
    List<Map<String, Object>> result = new ArrayList<>();
    for (Object item : (List<?>) rawData) {
        result.add(objectMapper.convertValue(item, Map.class));
    }
    return result;
}

    @Override
    public byte[] invetarioCsv() {

        String csv = "";
        
        Respuesta loteResp = lotesService.findAll();
        List<Map<String, Object>> loteData = toMapList(loteResp.getData());
        byte[] loteCsv = csvService.generateCsv(loteData);

        Respuesta barrilResp = barrilService.findAll();
        List<Map<String, Object>> barrilData = toMapList(barrilResp.getData());
        byte[] barrilCsv = csvService.generateCsv(barrilData);

        Respuesta cervezaResp = cervezaService.findAll();
        List<Map<String, Object>> cervezaData = toMapList(cervezaResp.getData());
        byte[] cervezaCsv = csvService.generateCsv(cervezaData);

        Respuesta accesorioResp = accesorioService.findAll();
        List<Map<String, Object>> accesorioData = toMapList(accesorioResp.getData());
        byte[] accesorioCsv = csvService.generateCsv(accesorioData);

        Respuesta maduradorResp = maduradorService.findAll();
        List<Map<String, Object>> maduradorData = toMapList(maduradorResp.getData());
        byte[] maduradorCsv = csvService.generateCsv(maduradorData);

        csv += "LOTE"+"\n"+byteToString(loteCsv)+"\n"+"BARRIL"+"\n"+byteToString(barrilCsv)+"\n"+"CERVEZA"+"\n"+byteToString(cervezaCsv)+"\n"+"ACCESORIO"+"\n"+byteToString(accesorioCsv)+"\n"+"MADURADOR"+"\n"+byteToString(maduradorCsv);
        csv=csv.replace("id,cerveza,", "id,cerveza,,,,,,,,");

        return csv.getBytes(StandardCharsets.UTF_8);
    }
}
