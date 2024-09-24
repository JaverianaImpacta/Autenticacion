package edu.javeriana.ingenieria.social.autenticacion.seguridad;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FiltroAutenticacionJWT extends OncePerRequestFilter{

    @Value("${ENCABEZADO}")
    private String ENCABEZADO;
    @Value("${SECRETO}")
    private String SECRETO;

    private final ConstructorToken constructorJwt;

    public FiltroAutenticacionJWT(ConstructorToken constructorJwt) {
        this.constructorJwt = constructorJwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest solicitud, HttpServletResponse respuesta, FilterChain cadenaFiltro) throws ServletException, IOException {
        if(validarEstructura(solicitud)){
            Claims intenciones = validarToken(solicitud);
            if(intenciones.get("aut") != null){
                establecerAutenticacion(intenciones);
            }else{
                SecurityContextHolder.clearContext();
            }
        }
        cadenaFiltro.doFilter(solicitud, respuesta);
    }

    private Claims validarToken(HttpServletRequest solicitud){
        String token = solicitud.getHeader(ENCABEZADO).replace(constructorJwt.getPREFIJO()+" ", "");
        return Jwts.parser().verifyWith(obtenerLlave(SECRETO)).build().parseSignedClaims(token).getPayload();
    }

    private void establecerAutenticacion(Claims claims) {
        List<String> autoridades = (List<String>) claims.get("aut");
        UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, autoridades.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(autenticacion);
    }

    private SecretKey obtenerLlave(String secreto){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secreto));
    }

    private boolean validarEstructura(HttpServletRequest request) {
        String encabezadoAutenticacion = request.getHeader(ENCABEZADO);
        return encabezadoAutenticacion != null && encabezadoAutenticacion.startsWith(constructorJwt.getPREFIJO());
    }
}
