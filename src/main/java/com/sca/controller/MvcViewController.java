package com.sca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindException;
import javax.servlet.http.HttpServletRequest;

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
    
    @Autowired
    com.sca.service.impl.MaduradorServiceImpl maduradorService;

    @Autowired
    com.sca.service.impl.AccesorioServiceImpl accesorioService;

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
    public String saveLote(Lote lote, Model model, HttpServletRequest request) {
        try {
            // If the form submitted a nested cerveza.id, load the managed Cerveza entity
            if (lote != null && lote.getCerveza() != null && lote.getCerveza().getId() != null) {
                try {
                    Object c = cervezaService.finById(lote.getCerveza().getId()).getData();
                    if (c instanceof com.sca.model.Cerveza) {
                        lote.setCerveza((com.sca.model.Cerveza) c);
                    } else {
                        lote.setCerveza(null);
                    }
                } catch (Exception ex) {
                    System.out.println("[DEBUG] failed to resolve cerveza id=" + lote.getCerveza().getId() + " : " + ex.getMessage());
                    lote.setCerveza(null);
                }
            }
            // Fallback: sometimes the binder doesn't instantiate nested bean; check request params
            if (lote != null && (lote.getCerveza() == null || lote.getCerveza().getId() == null)) {
                String cervezaIdStr = request.getParameter("cerveza.id");
                if (cervezaIdStr == null) {
                    cervezaIdStr = request.getParameter("cerveza");
                }
                if (cervezaIdStr != null && !cervezaIdStr.trim().isEmpty()) {
                    try {
                        Long cid = Long.parseLong(cervezaIdStr);
                        Object c = cervezaService.finById(cid).getData();
                        if (c instanceof com.sca.model.Cerveza) {
                            lote.setCerveza((com.sca.model.Cerveza) c);
                        } else {
                            lote.setCerveza(new com.sca.model.Cerveza());
                            lote.getCerveza().setId(cid);
                        }
                    } catch (Exception ex2) {
                        System.out.println("[DEBUG] fallback failed to parse/resolve cerveza id='" + cervezaIdStr + "' : " + ex2.getMessage());
                    }
                }
            }

            BindException be = new BindException(lote, "lote");
            // capture response to log status/body for debugging
            try {
                org.springframework.http.ResponseEntity<Object> resp = loteService.save(lote, be);
                System.out.println("[DEBUG] saveLote response status=" + resp.getStatusCode() + " body=" + resp.getBody());
            } catch (Exception inner) {
                System.out.println("[DEBUG] saveLote threw: " + inner.getMessage());
            }
        } catch (Exception e) {
            // log si se desea
            System.out.println("[DEBUG] outer saveLote exception: " + e.getMessage());
        }
        model.addAttribute("items", loteService.findAll().getData());
        return "lotes/fragments :: lista";
    }

    // Debug: return raw lotes as JSON (only in dev)
    @GetMapping("/debug/lotes")
    @org.springframework.web.bind.annotation.ResponseBody
    public Object debugLotes() {
        return java.util.Collections.singletonMap("data", loteService.findAll().getData());
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
            // Debug: print incoming cliente fields to help diagnose binding/validation issues
            try {
                System.out.println("[DEBUG] saveCliente called with cliente=" + cliente);
            } catch (Exception x) { /* ignore */ }
            BindException be = new BindException(cliente, "cliente");
            // capture response to check service result
            try {
                org.springframework.http.ResponseEntity<Object> resp = clienteService.save(cliente, be);
                System.out.println("[DEBUG] saveCliente response status=" + resp.getStatusCode() + " body=" + resp.getBody());
            } catch (Exception inner) {
                System.out.println("[DEBUG] clienteService.save threw: " + inner.getMessage());
            }
        } catch (Exception e) {
            System.out.println("[DEBUG] saveCliente outer exception: " + e.getMessage());
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
            // Ensure required fields have sensible defaults to avoid validation failures
            if (cerveza.getTipoCerveza() == null || cerveza.getTipoCerveza().trim().isEmpty()) {
                cerveza.setTipoCerveza("Otro");
            }
            if (cerveza.getGradoAlcoholico() == null) {
                cerveza.setGradoAlcoholico(4.5);
            }
            if (cerveza.getAmargorIbu() == null) {
                cerveza.setAmargorIbu(10.0);
            }
            if (cerveza.getEstado() == null || cerveza.getEstado().trim().isEmpty()) {
                cerveza.setEstado("Disponible");
            }
            if (cerveza.getPrecioPorLitro() == null) {
                cerveza.setPrecioPorLitro(0.0);
            }
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
    
    @GetMapping("/accesorios/list")
    public String accesoriosList(Model model) {
        model.addAttribute("items", accesorioService.findAll().getData());
        return "accesorios/fragments :: lista";
    }

    @GetMapping("/accesorios/form")
    public String accesorioForm(Model model) {
        model.addAttribute("accesorio", new com.sca.model.Accesorio());
        return "accesorios/fragments :: form";
    }

    @GetMapping("/accesorios/{id}")
    public String accesorioById(@PathVariable Long id, Model model) {
        model.addAttribute("accesorio", accesorioService.finById(id).getData());
        return "accesorios/fragments :: form";
    }

    @PostMapping("/accesorios/save")
    public String saveAccesorio(com.sca.model.Accesorio accesorio, Model model) {
        try {
            BindException be = new BindException(accesorio, "accesorio");
            accesorioService.save(accesorio, be);
        } catch (Exception e) {
            // ignore
        }
        model.addAttribute("items", accesorioService.findAll().getData());
        return "accesorios/fragments :: lista";
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
    
    @GetMapping("/maduradores/list")
    public String maduradoresList(Model model) {
        model.addAttribute("items", maduradorService.findAll().getData());
        return "maduradores/fragments :: lista";
    }

    @GetMapping("/maduradores/form")
    public String maduradorForm(Model model) {
        model.addAttribute("madurador", new com.sca.model.Madurador());
        model.addAttribute("lotes", loteService.findAll().getData());
        return "maduradores/fragments :: form";
    }

    @GetMapping("/maduradores/{id}")
    public String maduradorById(@PathVariable Long id, Model model) {
        model.addAttribute("madurador", maduradorService.finById(id).getData());
        model.addAttribute("lotes", loteService.findAll().getData());
        return "maduradores/fragments :: form";
    }

    @PostMapping("/maduradores/save")
    public String saveMadurador(com.sca.model.Madurador madurador, Model model, javax.servlet.http.HttpServletRequest request) {
        try {
            // If binder didn't create nested lote, attempt to read lote.id from request and resolve entity
            if (madurador != null && (madurador.getLote() == null || madurador.getLote().getId() == null)) {
                String loteIdStr = request.getParameter("lote.id");
                if (loteIdStr == null) {
                    loteIdStr = request.getParameter("lote");
                }
                if (loteIdStr != null && !loteIdStr.trim().isEmpty()) {
                    try {
                        Long lid = Long.parseLong(loteIdStr);
                        Object l = loteService.finById(lid).getData();
                        if (l instanceof com.sca.model.Lote) {
                            madurador.setLote((com.sca.model.Lote) l);
                        } else {
                            com.sca.model.Lote temp = new com.sca.model.Lote();
                            temp.setId(lid);
                            madurador.setLote(temp);
                        }
                    } catch (Exception ex) {
                        System.out.println("[DEBUG] failed to resolve lote id='" + loteIdStr + "' : " + ex.getMessage());
                    }
                }
            }

            BindException be = new BindException(madurador, "madurador");
            maduradorService.save(madurador, be);
        } catch (Exception e) {
            // ignore
        }
        model.addAttribute("items", maduradorService.findAll().getData());
        return "maduradores/fragments :: lista";
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
