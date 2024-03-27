package edu.javeriana.ingenieria.social.autenticacion.repositorio;

import edu.javeriana.ingenieria.social.autenticacion.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioAutenticacion extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findOneByCorreo(String correo);
}
