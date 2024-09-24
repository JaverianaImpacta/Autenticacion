package edu.javeriana.ingenieria.social.autenticacion.seguridad;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class ConfiguracionSeguridadWeb{

    private final FiltroAutenticacionJWT filtroAutenticacionJwt;
    @Value("${request-mapping.controller.csrf}")
    private String filtroCsrf;
    @Value("${request-mapping.controller.allowed}")
    private String filtroPublico;

    public ConfiguracionSeguridadWeb(FiltroAutenticacionJWT filtroAutenticacionJwt) {
        this.filtroAutenticacionJwt = filtroAutenticacionJwt;
    }

    @Bean
    public SecurityFilterChain cadenaFiltroSeguridad(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.ignoringRequestMatchers(filtroCsrf))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(filtroPublico).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filtroAutenticacionJwt, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
