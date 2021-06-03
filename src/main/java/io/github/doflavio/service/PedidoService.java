package io.github.doflavio.service;

import io.github.doflavio.domain.entity.Pedido;
import io.github.doflavio.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
