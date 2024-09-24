package edu.javeriana.ingenieria.social.autenticacion.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RespuestaError {
    private String mensaje;
    private Integer code;
}
