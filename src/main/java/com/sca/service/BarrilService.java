package com.sca.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import com.sca.model.Barril;
import com.sca.model.Respuesta;
import com.sca.model.Lote;

public interface BarrilService {

    ResponseEntity<Object> save(Barril barril, BindingResult bindingResult) throws BindException;

    Respuesta delete(Long id);

    Respuesta findAll();

    Respuesta findById(Long id);

    ResponseEntity<Object> update(Barril barril, BindingResult bindingResult) throws BindException;

    Respuesta findByEstado(String estado);

    Respuesta findByLote(Lote lote);

    Respuesta findByCervezaAndEstado(Long cervezaId, String estado);

    void marcarComoAlquilados(List<Long> barrilIds);

    // 🔹 Nuevo: devolver barriles a estado "Cargado"
    void marcarComoCargados(List<Long> barrilIds);

    // 🔹 Nuevo método que ya tenías
    Respuesta findDisponiblesByCerveza(Long cervezaId);
    
}
