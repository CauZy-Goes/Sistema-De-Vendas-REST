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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller //Spring Web
@RequestMapping("/api/clientes")
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
    @ResponseBody //reponde no body
    public String helloCliente(@PathVariable("nome") String nomeCliente){
        return String.format("Hello %s", nomeCliente);
    }

    @GetMapping("/{id}")
    @ResponseBody //transforma em json
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()){
            HttpHeaders headers = new HttpHeaders();
            headers.put("Authorization", Collections.singletonList("token"));
            ResponseEntity<Cliente> responseEntity = new ResponseEntity<>(cliente.get() , headers ,HttpStatus.OK);

            return  responseEntity;
//            ou
//            return ResponseEntity.ok(cliente.get()); //corpo da resposta http
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody // oque retorna
    public ResponseEntity<Cliente> save(@RequestBody /* oque envia */  Cliente cliente){
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clientes.findById(id);

        if(cliente.isPresent()){
            clientes.delete(cliente.get()); // o optinal precisa do .get()
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")//atualiza integralmente
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Integer id, /* so precisa da String "id" se for diferente */
                                 @RequestBody Cliente cliente){
        return clientes.findById(id).map( clienteExistente -> {
            cliente.setId(clienteExistente.getId());
            clientes.save(cliente);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build()); //suplier recebe nada mas retorna alguma coisa
    }

    @GetMapping
    public ResponseEntity find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //configura para encontrar os clientes atraves das propiedades
        // withIgnoreCasa = ignora o camel case, withStringMatcher.containing = se a string conter "ca" de "caua" ele aceita
        Example example = Example.of(filtro, matcher); //extrai as propiedades
        List<Cliente> lista = clientes.findAll(example);
        return ResponseEntity.ok(lista);
    }
}
