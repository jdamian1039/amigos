package com.unitec.amigos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorHola {
    //Este recurso es el hola mundo de un servicio rest que usa el metodo GET
    @GetMapping("/hola")
    public String hola(){
        return "Hola desde mi servicio REST";
    }

}
