package io.github.doflavio.domain.repository;

import io.github.doflavio.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {

}
