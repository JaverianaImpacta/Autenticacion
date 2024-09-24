package edu.javeriana.ingenieria.social.autenticacion.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RespuestaUsuario {
    public Integer id;
    public Long cedula;
    public String correo, token;
    public boolean activo;
}
