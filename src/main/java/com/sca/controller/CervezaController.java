package com.sca.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

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
import org.springframework.web.bind.annotation.RestController;

import com.sca.model.Cerveza;
import com.sca.model.Respuesta;
import com.sca.service.impl.CervezaServiceImpl;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

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

	// Nuevo endpoint para búsqueda por ID y respuesta HTML
    @GetMapping(value = "/cervezas/buscar-por-id/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String buscarPorIdHtml(@PathVariable Long id) {
        Respuesta respuesta = cervezasServiceImpl.finById(id);
        if (respuesta != null && respuesta.getData() != null) {
            Object data = respuesta.getData();
            Cerveza cerveza = null;
            // Si el servicio devuelve directamente una Cerveza
            if (data instanceof Cerveza) {
                cerveza = (Cerveza) data;
            // Si el servicio devuelve una lista y el primer elemento es una Cerveza
            } else if (data instanceof java.util.List) {
                java.util.List<?> lista = (java.util.List<?>) data;
                if (!lista.isEmpty() && lista.get(0) instanceof Cerveza) {
                    cerveza = (Cerveza) lista.get(0);
                }
            }
            if (cerveza != null) {
                // Evitar null pointer en campos opcionales
                String descripcion = cerveza.getDescripcion() != null ? cerveza.getDescripcion() : "-";
                String precio = cerveza.getPrecioPorLitro() != null ? cerveza.getPrecioPorLitro().toString() : "-";
                String amargor = cerveza.getAmargorIbu() != null ? cerveza.getAmargorIbu().toString() : "-";
                String tipo = cerveza.getTipoCerveza() != null ? cerveza.getTipoCerveza() : "-";
                String nombre = cerveza.getNombreCerveza() != null ? cerveza.getNombreCerveza() : "-";
                String estado = cerveza.getEstado() != null ? cerveza.getEstado() : "-";
                String grado = cerveza.getGradoAlcoholico() != null ? cerveza.getGradoAlcoholico().toString() : "-";
                return "<div class='card'><div class='card-body'>"
                    + "<h5 class='card-title'>Cerveza ID: " + cerveza.getId() + "</h5>"
                    + "<p class='card-text'><b>Nombre:</b> " + nombre + "</p>"
                    + "<p class='card-text'><b>Tipo:</b> " + tipo + "</p>"
                    + "<p class='card-text'><b>Grado Alcohólico:</b> " + grado + "</p>"
                    + "<p class='card-text'><b>Amargor IBU:</b> " + amargor + "</p>"
                    + "<p class='card-text'><b>Descripción:</b> " + descripcion + "</p>"
                    + "<p class='card-text'><b>Precio por Litro:</b> " + precio + "</p>"
                    + "<p class='card-text'><b>Estado:</b> " + estado + "</p>"
                    + "</div></div>";
            }
        }
        return "<div class='alert alert-danger'>No se encuentra</div>";
    }

    // Agregado: búsqueda por estado con respuesta HTML
    @GetMapping(value = "/cervezas/buscar-por-estado/{estado}", produces = MediaType.TEXT_HTML_VALUE)
    public String buscarPorEstadoHtml(@PathVariable String estado) {
        Respuesta respuesta = cervezasServiceImpl.findByEstado(estado);
        if (respuesta != null && respuesta.getData() != null) {
            Object data = respuesta.getData();
            java.util.List<?> lista = null;
            if (data instanceof java.util.List) {
                lista = (java.util.List<?>) data;
            } else if (data instanceof Cerveza) {
                lista = java.util.Arrays.asList(data);
            }
            if (lista != null && !lista.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("<div class='list-group'>");
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
