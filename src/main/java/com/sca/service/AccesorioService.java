package com.sca.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.sca.model.Accesorio;
import com.sca.model.Respuesta;

public interface AccesorioService {

	public ResponseEntity<Object> save(Accesorio accesorio, BindingResult bindingResult) throws BindException;
	
	public ResponseEntity<Object> update(Accesorio accesorio, BindingResult bindingResult) throws BindException;
	
	public Respuesta delete(Long id);
	
	public Respuesta findAll();
	
	public Respuesta finById(Long id);
	
	public Respuesta findAccesoriosPorEstado(String estado);
}