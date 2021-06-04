package io.github.doflavio.domain.repository;

import io.github.doflavio.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Usuarios extends JpaRepository<Usuario,Integer>{
    Optional<Usuario> findByLogin(String login);
}
