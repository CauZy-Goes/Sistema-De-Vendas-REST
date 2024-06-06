package io.github.cauzy.rest.controller;

import io.github.cauzy.domain.entity.Pedido;
import io.github.cauzy.domain.service.PedidoService;
import io.github.cauzy.rest.dto.PedidoDTO;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService pedidoService) {
        this.service = pedidoService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }
}
