package io.github.doflavio.rest.controller;

import io.github.doflavio.domain.entity.Cliente;
import io.github.doflavio.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING
                                    );
        Example example = Example.of(filtro,matcher);

        List<Cliente> lista = clientes.findAll(example);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);

        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id){
        Optional<Cliente> cliente = clientes.findById(id);

        if(cliente.isPresent()){
            clientes.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Cliente cliente){
        return clientes
                .findById(id)
                .map(clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(
            value= {"/hello/{nome}","/api/hello/outraforma/{nome}" },
            method = RequestMethod.GET,
            consumes = {"application/json","application/xml"},
            produces = {"application/json","application/xml"}

    )
    @ResponseBody
    public String helloCliente(@PathVariable("nome") String nomeCliente) {
        return String.format("Hello %s",nomeCliente);
    }

}

/*
HttpHeaders headers = new HttpHeaders();
headers.put("Autorization", Arrays.asList("token"));

ResponseEntity<Cliente> responseEntity =
        new ResponseEntity<>(cliente.get(), headers, HttpStatus.OK);

return responseEntity;
*/
