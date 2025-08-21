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

import com.sca.model.Barril;
import com.sca.model.Respuesta;
import com.sca.model.Lote;
import com.sca.repository.BarrilRepository;
import com.sca.service.BarrilService;

@Service
public class BarrilServiceImpl extends ResponseEntityExceptionHandler implements BarrilService{

Logger log = LoggerFactory.getLogger(String.class);
	
	@Autowired
	BarrilRepository barrilRepository;

	Respuesta respuesta;

	String resp = "";

	//El ExceptionHandler me sirve para recuperar o en viar el status del error del pedido
	@ExceptionHandler(BindException.class)
	@Override
	public ResponseEntity<Object> save(Barril barril, BindingResult bindingResult) throws BindException {
		respuesta = new Respuesta();
		try {
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Se agrego un Barril");
			respuesta.setData(barrilRepository.save(barril));
		} catch (Exception e) {
			respuesta.setCodigo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			respuesta.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
			respuesta.setDescripcion("No se pudo agregar el Barril");
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
			Barril barril = barrilRepository.findById(id).get();
			barrilRepository.deleteById(id);
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Se elimino un Barril");
			respuesta.setData(barril);
		} catch (Exception e) {
			respuesta.setCodigo("400");
			respuesta.setStatus("Error");
			respuesta.setDescripcion("No se pudo eliminar el Barril");
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
			respuesta.setDescripcion("Se muestran todos los Barrils");
			respuesta.setData(barrilRepository.findAll());
		} catch (Exception e) {
			respuesta.setCodigo("400");
			respuesta.setStatus("Error");
			respuesta.setDescripcion("No se pudieron mostrar los Barril");
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
			respuesta.setDescripcion("Datos de la Categoria");
			respuesta.setData(barrilRepository.findById(id));
		} catch (Exception e) {
			respuesta.setCodigo("400");
			respuesta.setStatus("Error");
			respuesta.setDescripcion("No se pudieron mostrar los datos del Barril");
			respuesta.setData(e.getMessage());
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<Object> update(Barril barril, BindingResult bindingResult) throws BindException {
		respuesta = new Respuesta();
		try {
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Se modificaron los datos del Barril");
			respuesta.setData(barrilRepository.save(barril));
		} catch (Exception e) {
			respuesta.setCodigo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			respuesta.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
			respuesta.setDescripcion("No se pudo modificar el Barril");
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
	public Respuesta findByEstado(String estado) {
		respuesta = new Respuesta();
		try {
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Datos de los Barriles por Estado");
			respuesta.setData(barrilRepository.findAll().stream().filter(n -> n.getEstado().equals(estado)));
		} catch (Exception e) {
			respuesta.setCodigo("400");
			respuesta.setStatus("Error");
			respuesta.setDescripcion("No se pudieron mostrar los datos de los Barriles");
			respuesta.setData(e.getMessage());
		}
		return respuesta;
	}

	@Override
	public Respuesta findByLote(Lote lote) {
		respuesta = new Respuesta();
		try {
			respuesta.setCodigo("200");
			respuesta.setStatus("Ok");
			respuesta.setDescripcion("Datos de los Barriles por Lote");
			respuesta.setData(barrilRepository.findAll().stream().filter(n -> n.getLote().equals(lote)));
		} catch (Exception e) {
			respuesta.setCodigo("400");
			respuesta.setStatus("Error");
			respuesta.setDescripcion("No se pudieron filtrar los de los Barriles");
			respuesta.setData(e.getMessage());
		}
		return respuesta;
	}
}

