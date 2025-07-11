package com.sierramaestra.controller;

import com.sierramaestra.model.Barril;
import com.sierramaestra.model.Lote;
import com.sierramaestra.service.BarrileServicio;
import com.sierramaestra.service.LoteServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping
public class LoteControlador {

    @Autowired
    private LoteServicio servicio;
    
    @Autowired
    private BarrileServicio barrileServicio;

    @GetMapping("/lote")
    public String listarTodosLosLotes(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(name = "mensajeError", required = false) String mensajeError,
                                      Model modelo) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Lote> lotesPage = servicio.listarTodosLosLotes(pageable);
        modelo.addAttribute("lotes", lotesPage.getContent());
        modelo.addAttribute("currentPage", page);
        modelo.addAttribute("totalPages", lotesPage.getTotalPages());
        modelo.addAttribute("estados", new String[]{"Activo", "Inactivo"});

        if (mensajeError != null) {
            modelo.addAttribute("mensajeError", mensajeError);
        }

        return "tabla_lote";
    }

    @PostMapping("/lote/{id}/cargarBarriles")
    public String cargarBarrilesEnLote(
        @PathVariable("id") Long id,
        @RequestParam(name = "barrilesSeleccionados", defaultValue = "") List<Long> barrilesIds,
        Model modelo
    ) {
        Lote lote = servicio.obtenerLotePorId(id);
        if (lote == null) {
            modelo.addAttribute("error", "Lote no encontrado.");
            return "404";
        }

        List<Barril> barriles = barrileServicio.listarTodosLosBarriles()
            .stream()
            .filter(b -> barrilesIds.contains(b.getId()) && "Limpio".equalsIgnoreCase(b.getEstado()))
            .toList();

        if (barriles.isEmpty()) {
            modelo.addAttribute("error", "No se encontraron barriles válidos para asignar.");
            List<Barril> barrilesCargados = barrileServicio.listarBarrilesPorLote(lote.getId());
            List<Barril> barrilesLimpios = barrileServicio.listarBarrilesPorEstado("Limpio");

            modelo.addAttribute("lote", lote);
            modelo.addAttribute("barrilesCargados", barrilesCargados);
            modelo.addAttribute("barrilesLimpios", barrilesLimpios);
            return "show_lote";
        }

        for (Barril barril : barriles) {
            barril.setEstado("Cargado");
            barril.setLote(lote);
            barrileServicio.actualizarBarril(barril);
        }

        lote.setEstado("Cargado");
        servicio.actualizarLote(lote);

        return "redirect:/lote/" + id;
    }

    @GetMapping("/lote/{id}")
    public String obtenerLotePorId(@PathVariable Long id, Model model) {
        Lote lote = servicio.obtenerLotePorId(id);
        if (lote == null) {
            model.addAttribute("error", "No se encontró un lote con el ID especificado.");
            return "error";
        }

        List<Barril> barrilesCargados = barrileServicio.listarBarrilesPorLote(lote.getId());
        List<Barril> barrilesLimpios = barrileServicio.listarBarrilesPorEstado("Limpio");

        model.addAttribute("lote", lote);
        model.addAttribute("barrilesCargados", barrilesCargados);
        model.addAttribute("barrilesLimpios", barrilesLimpios);
        return "show_lote";
    }

    @PostMapping("/lote")
    public String guardarLote(@ModelAttribute("lote") Lote lote) throws Exception {
        if (servicio.guardarLote(lote) != null)
            return "redirect:/lote";
        return "redirect:/lote/nuevo";
    }

    @GetMapping("/lote/nuevo")
    public String crearLoteFormulario(Model modelo) {
        Lote lote = new Lote();
        modelo.addAttribute("lote", lote);
        modelo.addAttribute("estados", new String[]{"Activo"});
        return "crear_lote"; // NO REDIRECT
    }


    @DeleteMapping("/{id}")
    public String eliminarLote(@PathVariable("id") Long id) {
        servicio.eliminarLote(id);
        return "redirect:/lote";
    }

    @GetMapping("/lote/editarLote/{id}")
    public String editarLote(@PathVariable("id") Long id, Model modelo) {
        Lote lote = servicio.obtenerLotePorId(id);
        modelo.addAttribute("lote", lote);
        modelo.addAttribute("estados", new String[]{"Activo", "Inactivo"});
        return "editar_lote"; // ✅ Devuelve directamente la vista
    }

    @PostMapping("/lote/editarLote/{id}")
    public String actualizarLote(@PathVariable("id") Long id, @ModelAttribute("lote") Lote lote) {
        lote.setId(id);
        servicio.actualizarLote(lote);
        return "redirect:/lote";
    }

    @GetMapping("/lote/buscar")
    public String buscarLotePorId(@RequestParam("id") Long id) {
        Lote lote = servicio.obtenerLotePorId(id);
        if (lote != null) {
            return "redirect:/lote?id=" + lote.getId();
        } else {
            return "redirect:/lote?mensajeError=Lote no encontrado con el ID proporcionado.";
        }
    }
}
