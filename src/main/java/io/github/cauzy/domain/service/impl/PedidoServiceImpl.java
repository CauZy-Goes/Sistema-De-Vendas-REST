package io.github.cauzy.domain.service.impl;

import io.github.cauzy.domain.repository.Pedidos;
import io.github.cauzy.domain.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private Pedidos repository;

    public PedidoServiceImpl(Pedidos repository) {
        this.repository = repository;
    }
}
