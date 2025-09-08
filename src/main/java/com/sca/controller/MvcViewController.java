package com.sca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindException;

import com.sca.model.Pedido;
import com.sca.model.Lote;
import com.sca.model.Barril;
import com.sca.service.impl.PedidoServiceImpl;
import com.sca.service.impl.LoteServiceImpl;

@Controller
public class MvcViewController {

    @Autowired
    PedidoServiceImpl pedidoService;

    @Autowired
    LoteServiceImpl loteService;

    @Autowired
    com.sca.service.impl.ClienteServiceImpl clienteService;

    @Autowired
    com.sca.service.impl.CervezaServiceImpl cervezaService;

    @Autowired
    com.sca.service.impl.BarrilServiceImpl barrilService;

    @Autowired
    com.sca.service.impl.AsociadosServiceImpl asociadosService;

    @GetMapping({"/","/home"})
    public String home(Model model) {
        model.addAttribute("title", "Sistema - Inicio");
    return "home";
    }

    // Pedidos
    @GetMapping("/pedidos")
    public String pedidosIndex(Model model) {
        model.addAttribute("title", "Pedidos");
        return "pedidos/index";
    }

    @GetMapping("/pedidos/list")
    public String pedidosList(Model model) {
        model.addAttribute("items", pedidoService.findAll().getData());
        return "pedidos/fragments :: lista";
    }

    @GetMapping("/pedidos/form")
    public String pedidoForm(Model model) {
        model.addAttribute("pedido", new Pedido());
    model.addAttribute("clientes", clienteService.findAll().getData());
    model.addAttribute("cervezas", cervezaService.findAll().getData());
    model.addAttribute("asociados", asociadosService.findAll().getData());
        return "pedidos/fragments :: form";
    }

    @GetMapping("/pedidos/{id}")
    public String pedidoById(@PathVariable Long id, Model model) {
        model.addAttribute("pedido", pedidoService.finById(id).getData());
    model.addAttribute("clientes", clienteService.findAll().getData());
    model.addAttribute("cervezas", cervezaService.findAll().getData());
    model.addAttribute("asociados", asociadosService.findAll().getData());
        return "pedidos/fragments :: form";
    }

    @PostMapping("/pedidos/save")
    public String savePedido(Pedido pedido, Model model) {
        try {
            BindException be = new BindException(pedido, "pedido");
            pedidoService.save(pedido, be);
        } catch (Exception e) {
            // log si se desea
        }
        model.addAttribute("items", pedidoService.findAll().getData());
        return "pedidos/fragments :: lista";
    }

    // Lotes
    @GetMapping("/lotes")
    public String lotesIndex(Model model) {
        model.addAttribute("title", "Lotes");
        return "lotes/index";
    }

    @GetMapping("/lotes/list")
    public String lotesList(Model model) {
        model.addAttribute("items", loteService.findAll().getData());
        return "lotes/fragments :: lista";
    }

    @GetMapping("/lotes/form")
    public String loteForm(Model model) {
        model.addAttribute("lote", new Lote());
    model.addAttribute("cervezas", cervezaService.findAll().getData());
        return "lotes/fragments :: form";
    }

    @GetMapping("/lotes/{id}")
    public String loteById(@PathVariable Long id, Model model) {
        model.addAttribute("lote", loteService.finById(id).getData());
    model.addAttribute("cervezas", cervezaService.findAll().getData());
        return "lotes/fragments :: form";
    }

    @PostMapping("/lotes/save")
    public String saveLote(Lote lote, Model model) {
        try {
            BindException be = new BindException(lote, "lote");
            loteService.save(lote, be);
        } catch (Exception e) {
            // log si se desea
        }
        model.addAttribute("items", loteService.findAll().getData());
        return "lotes/fragments :: lista";
    }

    // Otros módulos - vistas index básicas
    @GetMapping("/clientes")
    public String clientesIndex(Model model) {
        model.addAttribute("title", "Clientes");
        return "clientes/index";
    }

    @GetMapping("/clientes/list")
    public String clientesList(Model model) {
        model.addAttribute("items", clienteService.findAll().getData());
        return "clientes/fragments :: lista";
    }

    @GetMapping("/clientes/form")
    public String clienteForm(Model model) {
        model.addAttribute("cliente", new com.sca.model.Cliente());
        return "clientes/fragments :: form";
    }

    @GetMapping("/clientes/{id}")
    public String clienteById(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteService.finById(id).getData());
        return "clientes/fragments :: form";
    }

    @PostMapping("/clientes/save")
    public String saveCliente(com.sca.model.Cliente cliente, Model model) {
        try {
            BindException be = new BindException(cliente, "cliente");
            clienteService.save(cliente, be);
        } catch (Exception e) {
            // ignore for now
        }
        model.addAttribute("items", clienteService.findAll().getData());
        return "clientes/fragments :: lista";
    }

    @GetMapping("/cervezas")
    public String cervezasIndex(Model model) {
        model.addAttribute("title", "Cervezas");
        return "cervezas/index";
    }

    @GetMapping("/cervezas/list")
    public String cervezasList(Model model) {
        model.addAttribute("items", cervezaService.findAll().getData());
        return "cervezas/fragments :: lista";
    }

    @GetMapping("/cervezas/form")
    public String cervezaForm(Model model) {
        model.addAttribute("cerveza", new com.sca.model.Cerveza());
        return "cervezas/fragments :: form";
    }

    @GetMapping("/cervezas/{id}")
    public String cervezaById(@PathVariable Long id, Model model) {
        model.addAttribute("cerveza", cervezaService.finById(id).getData());
        return "cervezas/fragments :: form";
    }

    @PostMapping("/cervezas/save")
    public String saveCerveza(com.sca.model.Cerveza cerveza, Model model) {
        try {
            BindException be = new BindException(cerveza, "cerveza");
            cervezaService.save(cerveza, be);
        } catch (Exception e) {
            // ignore for now
        }
        model.addAttribute("items", cervezaService.findAll().getData());
        return "cervezas/fragments :: lista";
    }

    @GetMapping("/categorias")
    public String categoriasIndex(Model model) {
        model.addAttribute("title", "Categorías");
        return "categorias/index";
    }

    @GetMapping("/barriles")
    public String barrilesIndex(Model model) {
        model.addAttribute("title", "Barriles");
        return "barriles/index";
    }

    @GetMapping("/barriles/list")
    public String barrilesList(Model model) {
        model.addAttribute("items", barrilService.findAll().getData());
        return "barriles/fragments :: lista";
    }

    @GetMapping("/barriles/form")
    public String barrilForm(Model model) {
        model.addAttribute("barril", new Barril());
        model.addAttribute("lotes", loteService.findAll().getData());
        return "barriles/fragments :: form";
    }

    @GetMapping("/barriles/{id}")
    public String barrilById(@PathVariable Long id, Model model) {
        model.addAttribute("barril", barrilService.finById(id).getData());
        model.addAttribute("lotes", loteService.findAll().getData());
        return "barriles/fragments :: form";
    }

    @PostMapping("/barriles/save")
    public String saveBarril(Barril barril, Model model) {
        try {
            BindException be = new BindException(barril, "barril");
            barrilService.save(barril, be);
        } catch (Exception e) {
            // ignore
        }
        model.addAttribute("items", barrilService.findAll().getData());
        return "barriles/fragments :: lista";
    }

    @GetMapping("/accesorios")
    public String accesoriosIndex(Model model) {
        model.addAttribute("title", "Accesorios");
        return "accesorios/index";
    }

    @GetMapping("/asociados")
    public String asociadosIndex(Model model) {
        model.addAttribute("title", "Asociados");
        return "asociados/index";
    }

    @GetMapping("/asistencias")
    public String asistenciasIndex(Model model) {
        model.addAttribute("title", "Asistencias");
        return "asistencias/index";
    }

    @GetMapping("/asistencias-total")
    public String asistenciasTotalIndex(Model model) {
        model.addAttribute("title", "Asistencia Total");
        return "asistencias-total/index";
    }

    @GetMapping("/condiciones")
    public String condicionesIndex(Model model) {
        model.addAttribute("title", "Condiciones");
        return "condiciones/index";
    }

    @GetMapping("/dias")
    public String diasIndex(Model model) {
        model.addAttribute("title", "Días");
        return "dias/index";
    }

    @GetMapping("/firmas")
    public String firmasIndex(Model model) {
        model.addAttribute("title", "Firmas");
        return "firmas/index";
    }

    @GetMapping("/maduradores")
    public String maduradoresIndex(Model model) {
        model.addAttribute("title", "Maduradores");
        return "maduradores/index";
    }

    @GetMapping("/meses")
    public String mesesIndex(Model model) {
        model.addAttribute("title", "Meses");
        return "meses/index";
    }

    @GetMapping("/porcentajes-mes")
    public String porcentajesMesIndex(Model model) {
        model.addAttribute("title", "Porcentajes Mes");
        return "porcentajes-mes/index";
    }

    @GetMapping("/sueldos-basicos")
    public String sueldosBasicosIndex(Model model) {
        model.addAttribute("title", "Sueldos Básicos");
        return "sueldos-basicos/index";
    }

}
