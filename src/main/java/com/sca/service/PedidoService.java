package com.sca.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.sca.model.Pedido;
import com.sca.model.Respuesta;

public interface PedidoService {

	public ResponseEntity<Object> save(Pedido pedido, BindingResult bindingResult) throws BindException;
	
	public ResponseEntity<Object> update(Pedido pedido, BindingResult bindingResult) throws BindException;
	
	public Respuesta delete(Long id);
	
	public Respuesta findAll();
	
	public Respuesta finById(Long id);
}