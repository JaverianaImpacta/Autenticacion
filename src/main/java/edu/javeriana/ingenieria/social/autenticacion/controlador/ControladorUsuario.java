package edu.javeriana.ingenieria.social.autenticacion.controlador;

import edu.javeriana.ingenieria.social.autenticacion.dominio.Usuario;
import edu.javeriana.ingenieria.social.autenticacion.servicio.ServicioAutenticacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/autenticacion")
public class ControladorUsuario {
    @Autowired
    private ServicioAutenticacion servicio;

    @PostMapping(value="login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Usuario> validarUsuario(@RequestParam("correo") String correo, @RequestParam("contrasena") String contrasena){
        if(servicio.validarUsuario(correo, contrasena) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio.validarUsuario(correo, contrasena), HttpStatus.OK);
    }
}
