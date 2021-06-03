package io.github.doflavio.domain.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "cliente" )
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Campo Nome é obrigatório")
    @Column(name = "nome", length = 100)
    private String nome;

    @NotEmpty(message = "Campo CPF é obrigatório")
    @CPF(message = "Informe um CPF válido")
    @Column(name = "cpf", length = 11)
    private String cpf;

    @JsonIgnore
    @OneToMany( mappedBy = "cliente" , fetch = FetchType.LAZY )
    private Set<Pedido> pedidos;

    public Set<Pedido> getPedidos() {
        return pedidos;
    }
}