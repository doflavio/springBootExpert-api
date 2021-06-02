package io.github.doflavio.domain.repository;

import io.github.doflavio.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Clientes extends JpaRepository<Cliente, Integer> {

    @Query( "select c from Cliente c left join fetch c.pedidos where c.id = :id" )
    Cliente findClienteFetchPedidos(@Param("id")Integer id);

    List<Cliente> findByNomeLike(String nome);

    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);

    Cliente findOneByNome(String nome);

    boolean existsByNome(String nome);

    @Query(value=" select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    //Não funcionou assim com aspas simples, não entendi o motivo (like '%:nome%')
    @Query(value=" select * from Cliente c where c.nome like %:nome% ", nativeQuery = true)
    List<Cliente> encontrarPorNomeNativamente(@Param("nome") String nome);

    @Modifying
    //@Query(value = "delete from Cliente c where c.nome =:nome")
    void deleteByNome(String nome);
}
