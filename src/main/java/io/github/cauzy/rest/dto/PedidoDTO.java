package io.github.cauzy.rest.dto;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO { //data transfer object, mapei o objeto com propiedades simples
    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;
}
