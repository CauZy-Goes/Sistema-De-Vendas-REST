package io.github.cauzy;

import io.github.cauzy.domain.entity.Cliente;
import io.github.cauzy.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VendasApplication {

//    @Bean
//    public CommandLineRunner commandLineRunner(@Autowired Clientes clientes) {
//        return args -> {
//            Cliente cliente = new Cliente(null, "caio");
//            clientes.save(cliente);
//        };
//    }

    public static void main(String[] args) { // crl + alt + 0 + organiza import
        SpringApplication.run(VendasApplication.class, args);
    }
}
