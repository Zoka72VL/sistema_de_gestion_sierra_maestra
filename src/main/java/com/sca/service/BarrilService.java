package com.sca.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.sca.model.Barril;
import com.sca.model.Respuesta;
import com.sca.model.Lote;

public interface BarrilService {

	public ResponseEntity<Object> save(Barril barril, BindingResult bindingResult) throws BindException;
	
	public ResponseEntity<Object> update(Barril barril, BindingResult bindingResult) throws BindException;
	
	public Respuesta delete(Long id);
	
	public Respuesta findAll();
	
	public Respuesta finById(Long id);
	
	public Respuesta findByEstado(String estado);

	public Respuesta findByLote(Lote lote);
}