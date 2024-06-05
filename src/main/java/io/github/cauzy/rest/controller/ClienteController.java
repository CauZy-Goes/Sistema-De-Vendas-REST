package io.github.cauzy.rest.controller;

import io.github.cauzy.domain.entity.Cliente;
import io.github.cauzy.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController //ja vem com o @responsebody
@RequestMapping("api/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @RequestMapping( //mapeia um end point, define rota, define o metodo http
            value = {"/hello/{nome}", "/{nome}" }, //array de rotas
            method = RequestMethod.GET, //tipo de method http
            // so usa o pruduces e consumes quando n se trabalha com json, pois json ja é o tipo padrao
            consumes = {"application/json", "application/xml"}, //diz qual o formato que vai ser enviado
            produces = {"application/json", "application/xml"} //diz qual o formato que vai ser retornado
    )
//    @ResponseBody //reponde no body
    public String helloCliente(@PathVariable("nome") String nomeCliente){
        return String.format("Hello %s", nomeCliente);
    }

    @GetMapping("/{id}")
//    @ResponseBody //transforma em json
    public Cliente getClienteById(@PathVariable("id") Integer id){
        return clientes
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // define o codigo de created
//    @ResponseBody // oque retorna
    public Cliente save(@RequestBody /* oque envia */  Cliente cliente){
        return clientes.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @ResponseBody
    public void delete(@PathVariable("id") Integer id){
        clientes.findById(id)
                .map(cliente ->{
                        clientes.delete(cliente);
                        return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));

    }

    @PutMapping("/{id}")//atualiza integralmente
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @ResponseBody
    public void update(@PathVariable("id") Integer id, /* so precisa da String "id" se for diferente */
                       @RequestBody Cliente cliente){
        clientes.findById(id).
                map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return clienteExistente;})
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //configura para encontrar os clientes atraves das propiedades
        // withIgnoreCasa = ignora o camel case, withStringMatcher.containing = se a string conter "ca" de "caua" ele aceita
        Example example = Example.of(filtro, matcher); //extrai as propiedades
        List<Cliente> lista = clientes.findAll(example);
        return clientes.findAll(example);
    }
}
