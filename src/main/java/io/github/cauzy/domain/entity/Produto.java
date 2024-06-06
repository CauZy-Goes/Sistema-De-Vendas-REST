package io.github.cauzy.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@NoArgsConstructor // lombok
@AllArgsConstructor // lombok
@Data // lombok
// o @ data tem @Getter, @setter, @toString, equalsandhascoed
//@Getter // lombok
//@Setter // lombok
//@ToString // lombok
//@EqualsAndHashCode // lombok
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco_unitario")
    private BigDecimal preco;
}
