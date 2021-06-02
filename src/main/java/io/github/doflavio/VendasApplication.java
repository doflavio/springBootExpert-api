package io.github.doflavio;

import io.github.doflavio.domain.entity.Cliente;
import io.github.doflavio.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class VendasApplication {

    public static void main(String[] args) {
        System.out.println("Método main");
        SpringApplication.run(VendasApplication.class,args);
    }
}
/*
    @Bean
    public CommandLineRunner commandLineRunner(@Autowired Clientes clientes){
        return args -> {
            System.out.println("CommandLineRunner");
            Cliente c = new Cliente(null, "Fulano");
            clientes.save(c);
        };
    }
    */

