package io.github.doflavio.domain.repository;

import io.github.doflavio.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido,Integer> {
}
