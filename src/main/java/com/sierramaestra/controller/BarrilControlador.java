package com.sierramaestra.controller;

import com.sierramaestra.model.Barril;
import com.sierramaestra.service.BarrileServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Controller
@RequestMapping
public class BarrilControlador {

    @Autowired
    private BarrileServicio servicio;

    @GetMapping("/barril")
    public String listarTodosLosBarriles(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(name = "mensajeError", required = false) String mensajeError,
                                         Model modelo) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").ascending());
        Page<Barril> barrilesPage = servicio.listarTodosLosBarriles(pageable);

        modelo.addAttribute("barriles", barrilesPage.getContent());
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", barrilesPage.getTotalPages());
        modelo.addAttribute("estados", new String[]{"Cargado", "Alquilado", "Devuelto", "Limpio", "Inactivo"});

        if (mensajeError != null) {
            modelo.addAttribute("mensajeError", mensajeError);
        }

        return "tabla_barril";
    }

    @GetMapping("/barril/{id}")
    public String obtenerBarrilPorId(@PathVariable Long id, Model modelo) {
        Barril barril = servicio.obtenerBarrilPorId(id);
        if (barril == null) {
            modelo.addAttribute("mensajeError", "No se encontró un barril con el ID especificado.");
            return "redirect:/barril";
        }
        modelo.addAttribute("barril", barril);
        return "show_barril";
    }

    @GetMapping("/barril/nuevo")
    public String crearBarrilFormulario(Model modelo) {
        Barril barril = new Barril();
        barril.setEstado("Limpio"); // Estado por defecto
        modelo.addAttribute("barril", barril);
        modelo.addAttribute("estados", new String[]{"Cargado", "Alquilado", "Devuelto", "Limpio", "Inactivo"});
        return "crear_barril";
    }

    @PostMapping("/barril")
    public String guardarBarril(@ModelAttribute("barril") Barril barril) {
        servicio.guardarBarril(barril);
        return "redirect:/barril";
    }

    @GetMapping("/barril/editar/{id}")
    public String editarBarrilFormulario(@PathVariable Long id, Model modelo) {
        Barril barril = servicio.obtenerBarrilPorId(id);
        if (barril == null) {
            modelo.addAttribute("mensajeError", "No se encontró un barril con el ID especificado.");
            return "redirect:/barril";
        }
        modelo.addAttribute("barril", barril);
        modelo.addAttribute("estados", new String[]{"Cargado", "Alquilado", "Devuelto", "Limpio", "Inactivo"});
        return "editar_barril";
    }

    @PostMapping("/barril/{id}")
    public String actualizarBarril(@PathVariable Long id, @ModelAttribute("barril") Barril barril) {
        Barril barrilExistente = servicio.obtenerBarrilPorId(id);
        if (barrilExistente == null) {
            return "redirect:/barril?mensajeError=No se encontró el barril para actualizar";
        }
        barrilExistente.setLitros(barril.getLitros());
        barrilExistente.setEstado(barril.getEstado());
        barrilExistente.setNotas(barril.getNotas());
        servicio.actualizarBarril(barrilExistente);
        return "redirect:/barril";
    }

    @GetMapping("/barril/eliminar/{id}")
    public String eliminarBarril(@PathVariable Long id) {
        servicio.eliminarBarril(id);
        return "redirect:/barril";
    }

    @GetMapping("/barril/buscar")
    public String buscarBarrilPorId(@RequestParam("id") Long id, Model modelo) {
        Barril barril = servicio.obtenerBarrilPorId(id);
        if (barril != null) {
            modelo.addAttribute("barriles", List.of(barril));
            modelo.addAttribute("currentPage", 0);
            modelo.addAttribute("totalPages", 1);
        } else {
            modelo.addAttribute("mensajeError", "No se encontró el barril con ID: " + id);
            Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
            Page<Barril> barrilesPage = servicio.listarTodosLosBarriles(pageable);
            modelo.addAttribute("barriles", barrilesPage.getContent());
            modelo.addAttribute("currentPage", 0);
            modelo.addAttribute("totalPages", barrilesPage.getTotalPages());
        }
        modelo.addAttribute("estados", new String[]{"Cargado", "Alquilado", "Devuelto", "Limpio", "Inactivo"});
        return "tabla_barril";
    }
}

