package com.sca.service.impl;

import com.sca.service.InventarioService;
import com.sca.service.LoteService;
import com.sca.service.MaduradorService;
import com.sca.model.Respuesta;
import com.sca.service.AccesorioService;
import com.sca.service.BarrilService;
import com.sca.service.CervezaService;
import com.sca.service.CsvService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.charset.StandardCharsets;
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

    @Override
    public byte[] invetarioCsv() {

        String csv = "";
        
        Respuesta loteResp = lotesService.findAll();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> loteData = (List<Map<String, Object>>) loteResp.getData();
        byte[] loteCsv = csvService.generateCsv(loteData);

        Respuesta barrilResp = barrilService.findAll();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> barrilData = (List<Map<String, Object>>) barrilResp.getData();
        byte[] barrilCsv = csvService.generateCsv(barrilData);

        Respuesta cervezaResp = cervezaService.findAll();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> cervezaData = (List<Map<String, Object>>) cervezaResp.getData();
        byte[] cervezaCsv = csvService.generateCsv(cervezaData);

        Respuesta accesorioResp = accesorioService.findAll();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> accesorioData = (List<Map<String, Object>>) accesorioResp.getData();
        byte[] accesorioCsv = csvService.generateCsv(accesorioData);

        Respuesta maduradorResp = maduradorService.findAll();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> maduradorData = (List<Map<String, Object>>) maduradorResp.getData();
        byte[] maduradorCsv = csvService.generateCsv(maduradorData);

        csv += byteToString(loteCsv)+"\n"+byteToString(barrilCsv)+"\n"+byteToString(cervezaCsv)+"\n"+byteToString(accesorioCsv)+"\n"+byteToString(maduradorCsv);

        return csv.getBytes(StandardCharsets.UTF_8);
    }
}
