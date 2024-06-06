package io.github.cauzy.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO { //data transfer object, mapei o objeto com propiedades simples
    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;
}
