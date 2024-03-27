package edu.javeriana.ingenieria.social.autenticacion.servicio;

import edu.javeriana.ingenieria.social.autenticacion.dominio.Usuario;
import edu.javeriana.ingenieria.social.autenticacion.repositorio.RepositorioAutenticacion;
import edu.javeriana.ingenieria.social.autenticacion.seguridad.ConstructorToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutenticacion {

    @Autowired
    private RepositorioAutenticacion repositorio;

    public Usuario validarUsuario(String correo, String contrasena){
        Usuario usuario = encontrarUsuario(correo);
        if(usuario.getContrasena().equals(contrasena)){
            usuario.setToken(new ConstructorToken().armarJWToken(correo));
            return usuario;
        }else{
            System.out.println("Correo o contraseña incorrecta");
            return null;
        }
    }

    public Usuario encontrarUsuario(String correo){
        Usuario usuario = repositorio.findOneByCorreo(correo).orElseThrow(() -> new UsernameNotFoundException("El correo "+correo+" no corresponde a ningun usuario."));
        return usuario;
    }

}
