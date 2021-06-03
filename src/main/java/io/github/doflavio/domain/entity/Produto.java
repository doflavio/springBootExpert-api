package io.github.doflavio.domain.entity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Campo Descrição é obrigatório")
    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "Campo Preço é obrigatório")
    @Column(name = "preco_unitario")
    private BigDecimal preco;

}