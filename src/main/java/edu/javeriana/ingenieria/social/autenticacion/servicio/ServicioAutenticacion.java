package edu.javeriana.ingenieria.social.autenticacion.servicio;

import edu.javeriana.ingenieria.social.autenticacion.dominio.RespuestaUsuario;
import edu.javeriana.ingenieria.social.autenticacion.dominio.Usuario;
import edu.javeriana.ingenieria.social.autenticacion.repositorio.RepositorioAutenticacion;
import edu.javeriana.ingenieria.social.autenticacion.seguridad.ConstructorToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutenticacion {

    private RepositorioAutenticacion repositorio;
    private ConstructorToken constructorToken;

    public ServicioAutenticacion(RepositorioAutenticacion repositorio, ConstructorToken constructorToken) {
        this.repositorio = repositorio;
        this.constructorToken = constructorToken;
    }


    public RespuestaUsuario validarUsuario(Usuario usuario){
        Usuario usuarioIngresado = repositorio.findOneByCorreo(usuario.getCorreo()).orElse(null);
        if(usuarioIngresado == null || !usuarioIngresado.isActivo()){
            System.out.println("usuario inactivo");
            return null;
        }
        if(!new BCryptPasswordEncoder().matches(usuario.getContrasena(), usuarioIngresado.getContrasena())){
            return null;
        }
        return convertirUsuario(usuarioIngresado);
    }

    public RespuestaUsuario convertirUsuario(Usuario usuario){
        return new RespuestaUsuario(usuario.getId(), usuario.getCedula(), usuario.getCorreo(), constructorToken.obtenerToken(usuario.getId(), usuario.getCorreo()), usuario.isActivo());
    }

}
