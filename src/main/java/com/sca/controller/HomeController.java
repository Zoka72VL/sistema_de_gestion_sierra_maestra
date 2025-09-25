package com.sca.controller;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        // busca el archivo home.html dentro de /src/main/resources/templates
        return "home";
    }

    @GetMapping("/")
    public String root() {
        // si alguien entra a http://localhost:8080/ lo redirige al home
        return "redirect:/home";
    }
}
