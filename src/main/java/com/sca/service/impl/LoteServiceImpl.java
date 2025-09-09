package com.sca.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sca.model.Lote;
import com.sca.model.Respuesta;
import com.sca.repository.LoteRepository;
import com.sca.service.LoteService;

@Service
public class LoteServiceImpl extends ResponseEntityExceptionHandler implements LoteService{

Logger log = LoggerFactory.getLogger(String.class);
	
	@Autowired
	LoteRepository loteRepository;

	Respuesta respuesta;

	String resp = "";

	//El ExceptionHandler me sirve para recuperar o en viar el status del error del pedido
	@ExceptionHandler(BindException.class)
	@Override
	public ResponseEntity<Object> save(Lote lote, BindingResult bindingResult) throws BindException {
		// debug
		try {
			System.out.println("[DEBUG] LoteServiceImpl.save invoked with lote=" + lote + " bindingErrors=" + (bindingResult != null ? bindingResult.getErrorCount() : 0));
			if (bindingResult != null && bindingResult.hasErrors()) {
				bindingResult.getAllErrors().forEach(err -> System.out.println("[DEBUG] binding error: " + err.getDefaultMessage()));
			}
		} catch (Exception e) { System.out.println("[DEBUG] printing lote failed " + e.getMessage()); }
		respuesta = new Respuesta();
		try {
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Se agrego una Lote");
			respuesta.setData(loteRepository.save(lote));
		} catch (Exception e) {
			respuesta.setCodigo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			respuesta.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
			respuesta.setDescripcion("No se pudo agregar el Lote");
			if (bindingResult.hasErrors()) {
				bindingResult.getAllErrors().forEach(r -> {
					resp = resp + r.getDefaultMessage() + ";";
				});
				respuesta.setData(resp);
				resp = "";
				return handleExceptionInternal(e, respuesta, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);
			} else {
				respuesta.setData(e.getMessage());
				return handleExceptionInternal(e, respuesta, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);
			}
		}

		return new ResponseEntity<Object>(respuesta, null, HttpStatus.CREATED);
	}

	@Override
	public Respuesta delete(Long id) {
		respuesta = new Respuesta();
		System.out.println(id);
		try {
			Lote lote = loteRepository.findById(id).get();
			loteRepository.deleteById(id);
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Se elimino un Lote");
			respuesta.setData(lote);
		} catch (Exception e) {
			respuesta.setCodigo("400");
			respuesta.setStatus("Error");
			respuesta.setDescripcion("No se pudo eliminar el Lote");
			respuesta.setData(e.getMessage());
		}
		return respuesta;
	}

	@Override
	public Respuesta findAll() {
		respuesta = new Respuesta();
		try {
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Se muestran todos los Lotes");
			respuesta.setData(loteRepository.findAll());
		} catch (Exception e) {
			respuesta.setCodigo("400");
			respuesta.setStatus("Error");
			respuesta.setDescripcion("No se pudieron mostrar los Lotes");
			respuesta.setData(e.getMessage());
		}
		return respuesta;
	}

	@Override
	public Respuesta finById(Long id) {
		respuesta = new Respuesta();
		try {
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Datos del Lote");
			// Return the entity or null so templates can safely access properties like ${lote.id}
			respuesta.setData(loteRepository.findById(id).orElse(null));
		} catch (Exception e) {
			respuesta.setCodigo("400");
			respuesta.setStatus("Error");
			respuesta.setDescripcion("No se pudieron mostrar los datos del Lote");
			respuesta.setData(e.getMessage());
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<Object> update(Lote lote, BindingResult bindingResult) throws BindException {
		respuesta = new Respuesta();
		try {
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Se modificaron los datos del Lote");
			respuesta.setData(loteRepository.save(lote));
		} catch (Exception e) {
			respuesta.setCodigo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			respuesta.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
			respuesta.setDescripcion("No se pudo modificar la Lote");
			if (bindingResult.hasErrors()) {
				bindingResult.getAllErrors().forEach(r -> {
					resp = resp + r.getDefaultMessage() + ";";
				});
				respuesta.setData(resp);
				resp = "";
				return handleExceptionInternal(e, respuesta, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);
			} else {
				respuesta.setData(e.getMessage());
				return handleExceptionInternal(e, respuesta, new HttpHeaders(), HttpStatus.BAD_REQUEST, null);
			}
		}

		return new ResponseEntity<Object>(respuesta, null, HttpStatus.CREATED);
	}
}
