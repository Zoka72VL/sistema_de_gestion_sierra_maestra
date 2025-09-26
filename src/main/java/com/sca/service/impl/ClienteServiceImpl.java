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

import com.sca.model.Cliente;
import com.sca.model.Respuesta;
import com.sca.repository.ClienteRepository;
import com.sca.service.ClienteService;

@Service
public class ClienteServiceImpl extends ResponseEntityExceptionHandler implements ClienteService {

    Logger log = LoggerFactory.getLogger(String.class);

    @Autowired
    ClienteRepository clienteRepository;

    Respuesta respuesta;
    String resp = "";

    @ExceptionHandler(BindException.class)
    @Override
    public ResponseEntity<Object> save(Cliente cliente, BindingResult bindingResult) throws BindException {
        respuesta = new Respuesta();
        try {
            respuesta.setCodigo("200");
            respuesta.setStatus("Ok");
            respuesta.setDescripcion("Se agregó un Cliente");
            respuesta.setData(clienteRepository.save(cliente));
        } catch (Exception e) {
            respuesta.setCodigo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            respuesta.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            respuesta.setDescripcion("No se pudo agregar el Cliente");
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(r -> resp = resp + r.getDefaultMessage() + ";");
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
    public ResponseEntity<Object> update(Cliente cliente, BindingResult bindingResult) throws BindException {
        respuesta = new Respuesta();
        try {
            respuesta.setCodigo("200");
            respuesta.setStatus("Ok");
            respuesta.setDescripcion("Se modificaron los datos del Cliente");
            respuesta.setData(clienteRepository.save(cliente));
        } catch (Exception e) {
            respuesta.setCodigo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            respuesta.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            respuesta.setDescripcion("No se pudo modificar el Cliente");
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(r -> resp = resp + r.getDefaultMessage() + ";");
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
        try {
            Cliente cliente = clienteRepository.findById(id).get();
            clienteRepository.deleteById(id);
            respuesta.setCodigo("200");
            respuesta.setStatus("Ok");
            respuesta.setDescripcion("Se eliminó un Cliente");
            respuesta.setData(cliente);
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("No se pudo eliminar el Cliente");
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
            respuesta.setDescripcion("Se muestran todos los Clientes");
            respuesta.setData(clienteRepository.findAll());
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("No se pudieron mostrar los Clientes");
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
            respuesta.setDescripcion("Datos del Cliente");
            respuesta.setData(clienteRepository.findById(id).orElse(null));
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("No se pudieron mostrar los datos del Cliente");
            respuesta.setData(e.getMessage());
        }
        return respuesta;
    }

    @Override
    public Respuesta contarClientes() {
        respuesta = new Respuesta();
        try {
            respuesta.setCodigo("200");
            respuesta.setStatus("Ok");
            respuesta.setDescripcion("Cantidad de clientes");
            respuesta.setData(clienteRepository.count());
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("No se pudo contar los clientes");
            respuesta.setData(e.getMessage());
        }
        return respuesta;
    }

    // 🔹 Nuevo: login
    @Override
    public Respuesta login(String documento, String contrasenia) {
        respuesta = new Respuesta();
        try {
            Cliente cliente = clienteRepository.findByDocumentoAndContrasenia(documento, contrasenia);
            if (cliente != null) {
                respuesta.setCodigo("200");
                respuesta.setStatus("Ok");
                respuesta.setDescripcion("Login exitoso");
                respuesta.setData(cliente);
            } else {
                respuesta.setCodigo("401");
                respuesta.setStatus("Unauthorized");
                respuesta.setDescripcion("Documento o contraseña incorrectos");
                respuesta.setData(null);
            }
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("Error en login");
            respuesta.setData(e.getMessage());
        }
        return respuesta;
    }
}
