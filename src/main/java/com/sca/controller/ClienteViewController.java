package com.sca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteViewController {

    // Mapea la URL /clientela para mostrar la vista clientela.html
    @GetMapping("/clientela")
    public String clientelaPage() {
        return "clientela"; // busca clientela.html en src/main/resources/templates/
    }
}
