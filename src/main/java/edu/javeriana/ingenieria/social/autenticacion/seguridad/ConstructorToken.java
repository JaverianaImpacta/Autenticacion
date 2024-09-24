package edu.javeriana.ingenieria.social.autenticacion.seguridad;

import lombok.Getter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Getter
public class ConstructorToken {
    @Value("${PREFIJO}")
    private String PREFIJO;
    @Value("${SECRETO}")
    private String SECRETO;
    @Value("${EMISOR}")
    private String EMISOR;

    private SecretKey llave;

    public String obtenerToken(Integer id, String correo){
        this.llave = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRETO));
        List<GrantedAuthority> autoridades = AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
        Map<String, Object> intenciones = Map.of("aut", autoridades.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String token = Jwts
                .builder()
                .id(id.toString())
                .subject(correo)
                .issuer(EMISOR)
                .claims(intenciones)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(llave)
                .compact();
        return "Bearer " + token;
    }
}
