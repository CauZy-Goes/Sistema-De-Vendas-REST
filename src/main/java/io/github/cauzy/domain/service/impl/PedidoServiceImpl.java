package io.github.cauzy.domain.service.impl;

import io.github.cauzy.domain.entity.Cliente;
import io.github.cauzy.domain.entity.ItemPedido;
import io.github.cauzy.domain.entity.Pedido;
import io.github.cauzy.domain.entity.Produto;
import io.github.cauzy.domain.repository.Clientes;
import io.github.cauzy.domain.repository.ItemsPedido;
import io.github.cauzy.domain.repository.Pedidos;
import io.github.cauzy.domain.repository.Produtos;
import io.github.cauzy.domain.service.PedidoService;
import io.github.cauzy.exception.RegraNegocioException;
import io.github.cauzy.rest.dto.ItemPedidoDTO;
import io.github.cauzy.rest.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // lombok : gera constructor de parametros com final
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional // vai garantir que salva tudo ou se der erro dar rollback, ou ele faz tudo ou nao faz nada
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente invalido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items");
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository.findById(idProduto).orElseThrow(() -> new RegraNegocioException("Código de produto invalido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
