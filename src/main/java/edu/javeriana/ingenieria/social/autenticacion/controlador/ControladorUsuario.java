package edu.javeriana.ingenieria.social.autenticacion.controlador;

import edu.javeriana.ingenieria.social.autenticacion.dominio.Usuario;
import edu.javeriana.ingenieria.social.autenticacion.servicio.ServicioAutenticacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/autenticacion")
public class ControladorUsuario {
    @Autowired
    private ServicioAutenticacion servicio;

    @PostMapping(value="login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Usuario validarUsuario(@RequestParam("correo") String correo, @RequestParam("contrasena") String contrasena){
        return servicio.validarUsuario(correo, contrasena);
    }
}
