package io.github.doflavio.rest.controller;

import io.github.doflavio.domain.entity.ItemPedido;
import io.github.doflavio.domain.entity.Pedido;
import io.github.doflavio.rest.dto.InformacaoItemPedidoDto;
import io.github.doflavio.rest.dto.InformacoesPedidoDto;
import io.github.doflavio.rest.dto.PedidoDTO;
import io.github.doflavio.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDto getById(@PathVariable Integer id){
        return service.obterPedidoCompleto(id)
                .map( p -> converter(p))
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado.")

                );
    }

    private InformacoesPedidoDto converter(Pedido pedido){
        return InformacoesPedidoDto
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .items(converter(pedido.getItens()))
                .build();

    }

    private List<InformacaoItemPedidoDto> converter(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream()
                .map(
                        item -> InformacaoItemPedidoDto
                                .builder()
                        .descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
                ).collect(Collectors.toList());
    }

}
