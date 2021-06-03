package io.github.doflavio.service.impl;

import io.github.doflavio.domain.entity.Cliente;
import io.github.doflavio.domain.entity.ItemPedido;
import io.github.doflavio.domain.entity.Pedido;
import io.github.doflavio.domain.entity.Produto;
import io.github.doflavio.domain.enums.StatusPedido;
import io.github.doflavio.domain.repository.Clientes;
import io.github.doflavio.domain.repository.ItemsPedido;
import io.github.doflavio.domain.repository.Pedidos;
import io.github.doflavio.domain.repository.Produtos;
import io.github.doflavio.exception.PedidoNaoEncontradoException;
import io.github.doflavio.exception.RegraNegocioException;
import io.github.doflavio.rest.dto.ItemPedidoDTO;
import io.github.doflavio.rest.dto.PedidoDTO;
import io.github.doflavio.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;


    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCiente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCiente)
                .orElseThrow( () -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItens(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow( () -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItens(Pedido pedido,List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }
        return items.stream()
                    .map(dto -> {
                        Integer idProduto = dto.getProduto();

                        Produto produto = produtosRepository.findById(idProduto).
                                                            orElseThrow(() -> new RegraNegocioException("Código do produto inválido: " + idProduto));

                        ItemPedido itemPedido = new ItemPedido();
                        itemPedido.setQuantidade(dto.getQuantidade());
                        itemPedido.setPedido(pedido);
                        itemPedido.setProduto(produto);
                        return itemPedido;

                    }).collect(Collectors.toList());
    }
}
