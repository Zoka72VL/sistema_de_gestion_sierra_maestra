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

import com.sca.model.Pedido;
import com.sca.model.Respuesta;
import com.sca.repository.PedidoRepository;
import com.sca.service.PedidoService;

@Service
public class PedidoServiceImpl extends ResponseEntityExceptionHandler implements PedidoService {

    Logger log = LoggerFactory.getLogger(String.class);

    @Autowired
    PedidoRepository pedidoRepository;

    Respuesta respuesta;
    String resp = "";

    @ExceptionHandler(BindException.class)
    @Override
    public ResponseEntity<Object> save(Pedido pedido, BindingResult bindingResult) throws BindException {
        respuesta = new Respuesta();
        try {
            respuesta.setCodigo("200");
            respuesta.setStatus("Ok");
            respuesta.setDescripcion("Se agregó un Pedido");
            respuesta.setData(pedidoRepository.save(pedido));
        } catch (Exception e) {
            respuesta.setCodigo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            respuesta.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            respuesta.setDescripcion("No se pudo agregar el Pedido");
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
            Pedido pedido = pedidoRepository.findById(id).get();
            pedidoRepository.deleteById(id);
            respuesta.setCodigo("200");
            respuesta.setStatus("Ok");
            respuesta.setDescripcion("Se eliminó un Pedido");
            respuesta.setData(pedido);
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("No se pudo eliminar el Pedido");
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
            respuesta.setDescripcion("Se muestran todos los Pedidos");
            respuesta.setData(pedidoRepository.findAll());
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("No se pudieron mostrar los Pedidos");
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
            respuesta.setDescripcion("Datos del Pedido");
            respuesta.setData(pedidoRepository.findById(id).orElse(null));
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("No se pudieron mostrar los datos del Pedido");
            respuesta.setData(e.getMessage());
        }
        return respuesta;
    }

    @Override
    public ResponseEntity<Object> update(Pedido pedido, BindingResult bindingResult) throws BindException {
        respuesta = new Respuesta();
        try {
            respuesta.setCodigo("200");
            respuesta.setStatus("Ok");
            respuesta.setDescripcion("Se modificaron los datos del Pedido");
            respuesta.setData(pedidoRepository.save(pedido));
        } catch (Exception e) {
            respuesta.setCodigo(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            respuesta.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            respuesta.setDescripcion("No se pudo modificar el Pedido");
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

    // 🔹 Nuevo: pedidos por cliente
    @Override
    public Respuesta findByCliente(Long clienteId) {
        respuesta = new Respuesta();
        try {
            respuesta.setCodigo("200");
            respuesta.setStatus("Ok");
            respuesta.setDescripcion("Pedidos del cliente " + clienteId);
            respuesta.setData(pedidoRepository.findByClienteId(clienteId));
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("No se pudieron obtener los pedidos");
            respuesta.setData(e.getMessage());
        }
        return respuesta;
    }

    // 🔹 Nuevo: cancelar pedido
    @Override
    public Respuesta cancel(Long id) {
        respuesta = new Respuesta();
        try {
            Pedido pedido = pedidoRepository.findById(id).orElse(null);
            if (pedido != null) {
                if ("Pendiente".equalsIgnoreCase(pedido.getEstado())) {
                    pedido.setEstado("Cancelado");
                    pedidoRepository.save(pedido);
                    respuesta.setCodigo("200");
                    respuesta.setStatus("Ok");
                    respuesta.setDescripcion("Pedido cancelado correctamente");
                    respuesta.setData(pedido);
                } else {
                    respuesta.setCodigo("400");
                    respuesta.setStatus("Error");
                    respuesta.setDescripcion("Solo se pueden cancelar pedidos en estado Pendiente");
                    respuesta.setData(null);
                }
            } else {
                respuesta.setCodigo("404");
                respuesta.setStatus("Not Found");
                respuesta.setDescripcion("Pedido no encontrado");
                respuesta.setData(null);
            }
        } catch (Exception e) {
            respuesta.setCodigo("400");
            respuesta.setStatus("Error");
            respuesta.setDescripcion("Error al cancelar el pedido");
            respuesta.setData(e.getMessage());
        }
        return respuesta;
    }
}
