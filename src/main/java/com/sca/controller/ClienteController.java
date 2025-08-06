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

import com.sca.model.Cliente;
import com.sca.model.Respuesta;
import com.sca.service.impl.ClienteServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "Cliente")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@Slf4j
public class ClienteController {

	// Logger log = LoggerFactory.getLogger(String.class);
	
	@Autowired
	ClienteServiceImpl clientesServiceImpl;
	
	@PostMapping(value = "/addCliente", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Agrega un Cliente", notes = "Esta operación agrega un Cliente a la base de datos")
	public ResponseEntity<Object> addCliente(@RequestBody @Validated Cliente cliente, BindingResult bindingResult) throws BindException{
		return clientesServiceImpl.save(cliente,bindingResult);
	}
	
	@GetMapping(value = "/getAllCliente", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consultar Cliente", notes = "Esta operación devuelve todos los Cliente a la base de datos")
	public Respuesta getAllCliente() {
		return clientesServiceImpl.findAll();
	}
	
	@GetMapping(value = "/getByIdCliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consultar Cliente por id", notes = "Esta operación consulta un Cliente por su identificador personal")
	public Respuesta getByIdCliente(@PathParam("id") @PathVariable Long id) {
		return clientesServiceImpl.finById(id);
	}
	
	@DeleteMapping(value = "/deleteCliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Eliminar un Clientes", notes = "Esta operación elimina un Cliente de la base de datos")
	public Respuesta deleteCliente(@PathParam("id") @PathVariable Long id) {
		return clientesServiceImpl.delete(id);
	}
	
	@PutMapping(value = "/updateCliente", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Actualizar un Cliente", notes = "Esta operación actualiza un Cliente a la base de datos")
	public ResponseEntity<Object> updateCliente(@RequestBody Cliente cliente, BindingResult bindingResult) throws BindException {
		return clientesServiceImpl.update(cliente, bindingResult);
	}
}
