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
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@Slf4j
public class CervezaController {

	// Logger log = LoggerFactory.getLogger(String.class);
	
	@Autowired
	CervezaServiceImpl cervezasServiceImpl;
	
	@PostMapping(value = "/addCerveza", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Agrega un Cerveza", notes = "Esta operación agrega una Cerveza a la base de datos")
	public ResponseEntity<Object> addCerveza(@RequestBody @Validated Cerveza cerveza, BindingResult bindingResult) throws BindException{
		return cervezasServiceImpl.save(cerveza,bindingResult);
	}
	
	@GetMapping(value = "/getAllCerveza", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consultar Cerveza", notes = "Esta operación devuelve todos las Cerveza a la base de datos")
	public Respuesta getAllCerveza() {
		return cervezasServiceImpl.findAll();
	}
	
	@GetMapping(value = "/getByIdCerveza/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consultar Cerveza por id", notes = "Esta operación consulta una Cerveza por su identificador personal")
	public Respuesta getByIdCerveza(@PathParam("id") @PathVariable Long id) {
		return cervezasServiceImpl.finById(id);
	}
	
	@DeleteMapping(value = "/deleteCerveza/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Eliminar un Cervezas", notes = "Esta operación elimina una Cerveza de la base de datos")
	public Respuesta deleteCerveza(@PathParam("id") @PathVariable Long id) {
		return cervezasServiceImpl.delete(id);
	}
	
	@PutMapping(value = "/updateCerveza", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Actualizar un Cerveza", notes = "Esta operación actualiza una Cerveza a la base de datos")
	public ResponseEntity<Object> updateCerveza(@RequestBody Cerveza cerveza, BindingResult bindingResult) throws BindException {
		return cervezasServiceImpl.update(cerveza, bindingResult);
	}

    //Busqueda por ID
	@GetMapping(value = "/cervezas/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "Consultar cerveza por id (JSON)", notes = "Devuelve la cerveza solicitada en formato JSON")
    public Respuesta buscarPorId(@PathVariable Long id) {
        // usa la misma instancia de servicio que ya tenés en el controller
        return cervezasServiceImpl.finById(id);
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

    Row header = sheet.createRow(0);
    header.createCell(0).setCellValue("ID");
    header.createCell(1).setCellValue("Nombre");
    header.createCell(2).setCellValue("Tipo");
    header.createCell(3).setCellValue("Estado");

    int rowNum = 1;
    for (Cerveza c : cervezas) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(c.getId());
        row.createCell(1).setCellValue(c.getNombreCerveza());
        row.createCell(2).setCellValue(c.getTipoCerveza());
        row.createCell(3).setCellValue(c.getEstado());
    }

    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setHeader("Content-Disposition", "attachment; filename=cervezas_" + estado + ".xlsx");
    workbook.write(response.getOutputStream());
    workbook.close();
}
}
