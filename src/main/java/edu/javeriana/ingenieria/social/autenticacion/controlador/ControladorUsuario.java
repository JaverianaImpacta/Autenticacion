package edu.javeriana.ingenieria.social.autenticacion.controlador;

import edu.javeriana.ingenieria.social.autenticacion.dominio.RespuestaError;
import edu.javeriana.ingenieria.social.autenticacion.dominio.RespuestaUsuario;
import edu.javeriana.ingenieria.social.autenticacion.dominio.Usuario;
import edu.javeriana.ingenieria.social.autenticacion.servicio.ServicioAutenticacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="${request-mapping.controller.client}")
@RestController
@RequestMapping("${request-mapping.controller.app}")
public class ControladorUsuario {

    private final ServicioAutenticacion servicio;

    public ControladorUsuario(ServicioAutenticacion servicio) {
        this.servicio = servicio;
    }

    @PostMapping("permitido/login")
    public ResponseEntity<?> ingresar(@RequestBody Usuario usuario){
        if(usuario == null){
            return new ResponseEntity<>(new RespuestaError("Correo o contraseña faltante", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        RespuestaUsuario aux = servicio.validarUsuario(usuario);
        if(aux == null){
            return new ResponseEntity<>(new RespuestaError("Correo o contraseña incorrecta, por favor, verifica las credenciales e intenta nuevamente",  HttpStatus.BAD_REQUEST.value()),  HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(aux, HttpStatus.OK);
    }
}
