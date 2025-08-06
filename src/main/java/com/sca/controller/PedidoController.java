package com.sca.controller;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.sca.model.Pedido;
import com.sca.model.Respuesta;
import com.sca.service.impl.PedidoServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(tags = "Pedido")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@Slf4j
public class PedidoController {

Logger log = LoggerFactory.getLogger(String.class);
	
	@Autowired
	PedidoServiceImpl pedidosServiceImpl;
	
	@PostMapping(value = "/addPedido", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Agrega un Pedido", notes = "Esta operación agrega un Pedido a la base de datos")
	public ResponseEntity<Object> addPedido(@RequestBody @Validated Pedido pedido, BindingResult bindingResult) throws BindException{
		return pedidosServiceImpl.save(pedido,bindingResult);
	}
	
	@GetMapping(value = "/getAllPedido", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consultar Pedido", notes = "Esta operación devuelve todos los Pedido a la base de datos")
	public Respuesta getAllPedido() {
		return pedidosServiceImpl.findAll();
	}
	
	@GetMapping(value = "/getByIdPedido/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Consultar Pedido por id", notes = "Esta operación consulta un Pedido por su identificador personal")
	public Respuesta getByIdPedido(@PathParam("id") @PathVariable Long id) {
		return pedidosServiceImpl.finById(id);
	}
	
	@DeleteMapping(value = "/deletePedido/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Eliminar un Pedidos", notes = "Esta operación elimina un Pedido de la base de datos")
	public Respuesta deletePedido(@PathParam("id") @PathVariable Long id) {
		return pedidosServiceImpl.delete(id);
	}
	
	@PutMapping(value = "/updatePedido", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Actualizar un Pedido", notes = "Esta operación actualiza un Pedido a la base de datos")
	public ResponseEntity<Object> updatePedido(@RequestBody Pedido pedido, BindingResult bindingResult) throws BindException {
		return pedidosServiceImpl.update(pedido, bindingResult);
	}
}
