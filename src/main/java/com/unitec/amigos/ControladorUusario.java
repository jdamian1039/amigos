package com.unitec.amigos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorUusario {
    //Aqui va un metodo que representa cada uno de los estados que vamos a transferir (GET, POST, PUT, DELETE)

    //Uso de la inversión de control
    @Autowired RepositorioUsuario repUsuario;

    //Implementar código para gardar usuario en mongodb
    @PostMapping("/usuario")
    public Estatus guardar(@RequestBody String json)throws Exception{//equivalente a try catch
        //              valor peticion cliente   probablemente algo dentro del objeto puede producir un error dificil de intuir
        //Leer y convertir el el objeto json a objeto java
        ObjectMapper mapper=new ObjectMapper();
        Usuario u=mapper.readValue(json, Usuario.class);
        //El usuario en formato json lo guardamos en mongodb
        repUsuario.save(u);
        //Crear un objeto tipo estatus y retornarlo al cliente(android o postman)
        Estatus estatus =new Estatus();
        estatus.setSuccess(true);
        estatus.setMensaje("USUARIO GUARDADO");
        return estatus;
    }

    @GetMapping("/usuario/{id}")
    public Usuario obtenerPorId(@PathVariable String id ){
        //Leer un usuario con el método findById pasando como argumento el id que se quiere(email)
        //Apoyandose de repUsuario
        Usuario u=repUsuario.findById(id).get();

        return u;
    }

    @GetMapping("/usuario")
    public List<Usuario> buscarAll(){
        return repUsuario.findAll();
    }

    //Método para actualizar
    @PutMapping("/usuario")
    public Estatus actulizar(@RequestBody String json)throws Exception{ //requestbody manda llamar todo un objeto
        //Primero verificar que exista, primero se busca
        ObjectMapper mapper=new ObjectMapper();
        Estatus e=new Estatus();
        Usuario u=mapper.readValue(json, Usuario.class);
        if(repUsuario.findById(u.getEmail()).isPresent()){
            repUsuario.save(u);
            e.setMensaje("USUARIO ACTUALIZADO");
            e.setSuccess(true);
        }
        else{
            e.setMensaje("USUARIO NO EXISTE");
            e.setSuccess(false);
        }
        return e;
    }
    @DeleteMapping("/usuario/{id}")
    public Estatus borrar(@PathVariable String id){ //pathvariable solo manda llamar un dato
        Estatus estatus=new Estatus();
        if (repUsuario.findById(id).isPresent()){
            repUsuario.deleteById(id);
            estatus.setSuccess(true);
            estatus.setMensaje("USUARIO BORRADO");
        }
        else{
            estatus.setSuccess(false);
            estatus.setMensaje("USUARIO NO ENCONTRADO");
        }
        return estatus;
    }
}
