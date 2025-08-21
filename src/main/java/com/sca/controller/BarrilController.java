package com.sca.controller;

import javax.websocket.server.PathParam;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
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

import com.sca.model.Barril;
import com.sca.model.Respuesta;
import com.sca.model.Lote;
import com.sca.service.impl.BarrilServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "Barril")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@Slf4j
public class BarrilController {

	// Logger log = LoggerFactory.getLogger(String.class);
	
	@Autowired
	BarrilServiceImpl barrilsServiceImpl;
	
	@PostMapping(value = "/addBarril", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Agrega un Barril", notes = "Esta operación agrega un Barril a la base de datos")
	public ResponseEntity<Object> addBarril(@RequestBody @Validated Barril barril, BindingResult bindingResult) throws BindException{
		return barrilsServiceImpl.save(barril,bindingResult);
	}
	
	@GetMapping(value = "/getAllBarril", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consultar Barril", notes = "Esta operación devuelve todos los Barril a la base de datos")
	public Respuesta getAllBarril() {
		return barrilsServiceImpl.findAll();
	}
	
	@GetMapping(value = "/getByIdBarril/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consultar Barril por id", notes = "Esta operación consulta un Barril por su identificador personal")
	public Respuesta getByIdBarril(@PathParam("id") @PathVariable Long id) {
		return barrilsServiceImpl.finById(id);
	}
	
	@DeleteMapping(value = "/deleteBarril/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Eliminar un Barrils", notes = "Esta operación elimina un Barril de la base de datos")
	public Respuesta deleteBarril(@PathParam("id") @PathVariable Long id) {
		return barrilsServiceImpl.delete(id);
	}
	
	@PutMapping(value = "/updateBarril", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Actualizar un Barril", notes = "Esta operación actualiza un Barril a la base de datos")
	public ResponseEntity<Object> updateBarril(@RequestBody Barril Barril, BindingResult bindingResult) throws BindException {
		return barrilsServiceImpl.update(Barril, bindingResult);
	}

	@GetMapping(value = "/findByEstado/{estado}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consultar Barriles por Estado", notes = "Esta operación devuelve todos los Barriles filtrados por estado")
    public Respuesta findByEstado(@PathVariable String estado) {
       return barrilsServiceImpl.findByEstado(estado);
	}

	@GetMapping(value = "/findByLote/{lote}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Consultar Barriles por Lote", notes = "Esta operación devuelve todos los Barriles filtrados por lote")
    public Respuesta findByLote(@PathVariable Lote lote) {
       return barrilsServiceImpl.findByLote(lote);
	}
}
