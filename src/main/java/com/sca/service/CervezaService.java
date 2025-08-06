package com.sca.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.sca.model.Cerveza;
import com.sca.model.Respuesta;

public interface CervezaService {

	public ResponseEntity<Object> save(Cerveza cerveza, BindingResult bindingResult) throws BindException;
	
	public ResponseEntity<Object> update(Cerveza cerveza, BindingResult bindingResult) throws BindException;
	
	public Respuesta delete(Long id);
	
	public Respuesta findAll();
	
	public Respuesta finById(Long id);
}