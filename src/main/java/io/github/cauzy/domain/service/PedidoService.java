package io.github.cauzy.domain.service;

import io.github.cauzy.domain.entity.Pedido;
import io.github.cauzy.domain.repository.Pedidos;
import io.github.cauzy.rest.dto.PedidoDTO;
import org.springframework.stereotype.Service;

@Service
public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}
