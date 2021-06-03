package io.github.doflavio.service;

import io.github.doflavio.domain.entity.Pedido;
import io.github.doflavio.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}
