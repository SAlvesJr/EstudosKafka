package br.com.alura.microservice.cliente.controller;

import br.com.alura.microservice.cliente.model.Cliente;
import br.com.alura.microservice.cliente.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;

    }

    @GetMapping("/{email}")
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(clienteService.getClientByEmail(email));
    }
}
