package io.github.doflavio.rest.controller;

import io.github.doflavio.domain.entity.Cliente;
import io.github.doflavio.domain.repository.Clientes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);

        if(cliente.isPresent()){
            /*
            HttpHeaders headers = new HttpHeaders();
            headers.put("Autorization", Arrays.asList("token"));

            ResponseEntity<Cliente> responseEntity =
                    new ResponseEntity<>(cliente.get(), headers, HttpStatus.OK);

            return responseEntity;
            */
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();

    }



    @RequestMapping(value="/hello/{nome}", method = RequestMethod.GET)
    @ResponseBody
    public String helloCliente(@PathVariable("nome") String nomeCliente) {
        return String.format("Hello %s",nomeCliente);
    }
/*
    @RequestMapping(
            value= {"/hello/{nome}","/api/hello/outraforma/{nome}" },
            method = RequestMethod.GET,
            consumes = {"application/json","application/xml"},
            produces = {"application/json","application/xml"}

    )
    */


}
