package com.sca.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.sca.model.Cerveza;
import com.sca.model.Respuesta;
import com.sca.service.impl.CervezaServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "Cerveza")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Slf4j
public class CervezaController {

    @Autowired
    private CervezaServiceImpl cervezasServiceImpl;

    @PostMapping(value = "/addCerveza", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Agrega una Cerveza", notes = "Esta operación agrega una Cerveza a la base de datos")
    public ResponseEntity<Object> addCerveza(@RequestBody @Validated Cerveza cerveza, BindingResult bindingResult) throws BindException {
        return cervezasServiceImpl.save(cerveza, bindingResult);
    }

    @GetMapping(value = "/getAllCerveza", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consultar todas las Cervezas", notes = "Devuelve todas las Cervezas de la base de datos")
    public Respuesta getAllCerveza() {
        return cervezasServiceImpl.findAll();
    }

    @GetMapping(value = "/getByIdCerveza/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consultar Cerveza por id", notes = "Consulta una Cerveza por su ID")
    public Respuesta getByIdCerveza(@PathParam("id") @PathVariable Long id) {
        return cervezasServiceImpl.findById(id);
    }

    @DeleteMapping(value = "/deleteCerveza/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Eliminar una Cerveza", notes = "Elimina una Cerveza de la base de datos")
    public Respuesta deleteCerveza(@PathParam("id") @PathVariable Long id) {
        return cervezasServiceImpl.delete(id);
    }

    @PutMapping(value = "/updateCerveza", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Actualizar una Cerveza", notes = "Actualiza una Cerveza en la base de datos")
    public ResponseEntity<Object> updateCerveza(@RequestBody Cerveza cerveza, BindingResult bindingResult) throws BindException {
        return cervezasServiceImpl.update(cerveza, bindingResult);
    }

    // 🔍 Buscar por Estado en formato HTML
    @GetMapping(value = "/cervezas/buscar-por-estado/{estado}", produces = MediaType.TEXT_HTML_VALUE)
    public String buscarPorEstadoHtml(@PathVariable String estado) {
        Respuesta respuesta = cervezasServiceImpl.findByEstado(estado);
        if (respuesta != null && respuesta.getData() != null) {
            Object data = respuesta.getData();
            List<?> lista = null;
            if (data instanceof List) {
                lista = (List<?>) data;
            } else if (data instanceof Cerveza) {
                lista = java.util.Arrays.asList(data);
            }

            if (lista != null && !lista.isEmpty()) {
                StringBuilder sb = new StringBuilder("<div class='list-group'>");
                for (Object obj : lista) {
                    if (obj instanceof Cerveza) {
                        Cerveza cerveza = (Cerveza) obj;
                        sb.append("<div class='list-group-item'>")
                                .append("<b>ID:</b> ").append(cerveza.getId()).append(" | ")
                                .append("<b>Nombre:</b> ").append(cerveza.getNombreCerveza()).append(" | ")
                                .append("<b>Estado:</b> ").append(cerveza.getEstado())
                                .append("</div>");
                    }
                }
                sb.append("</div>");
                return sb.toString();
            }
        }
        return "<div class='alert alert-danger'>No se encontraron cervezas con ese estado</div>";
    }
    //Busqueda por ID
	@GetMapping(value = "/cervezas/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "Consultar cerveza por id (JSON)", notes = "Devuelve la cerveza solicitada en formato JSON")
    public Respuesta buscarPorId(@PathVariable Long id) {
        // usa la misma instancia de servicio que ya tenés en el controller
        return cervezasServiceImpl.findById(id);
    }


    // Imports ya estaban: MediaType está importado en tu archivo
    @GetMapping(value = "/cervezas/buscar-por-estado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consultar cervezas por estado", notes = "Devuelve la lista de cervezas filtradas por estado en JSON")
    public Respuesta buscarPorEstado(@PathVariable String estado) {
        return cervezasServiceImpl.findByEstado(estado);
    }


    @GetMapping("/cervezas/exportar-estado-excel/{estado}")
    public void exportarExcel(@PathVariable String estado, HttpServletResponse response) throws IOException {
    List<Cerveza> cervezas = (List<Cerveza>) cervezasServiceImpl.findByEstado(estado).getData();


        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cervezas");

        // Cabecera
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre");
        header.createCell(2).setCellValue("Tipo");
        header.createCell(3).setCellValue("Estado");

        // Filas
        int rowNum = 1;
        for (Cerveza c : cervezas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(c.getId());
            row.createCell(1).setCellValue(c.getNombreCerveza());
            row.createCell(2).setCellValue(c.getTipoCerveza());
            row.createCell(3).setCellValue(c.getEstado());
        }

        // Configurar respuesta
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=cervezas_" + estado + ".xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
