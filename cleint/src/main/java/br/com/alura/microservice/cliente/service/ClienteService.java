package br.com.alura.microservice.cliente.service;

import br.com.alura.microservice.cliente.exceptions.ObjectNotFoundException;
import br.com.alura.microservice.cliente.model.Cliente;
import br.com.alura.microservice.cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente getClientByEmail(String email) {
        var cli = clienteRepository.findByEmail(email);

        if (cli == null) {
            throw new ObjectNotFoundException(
                    "Objeto não encontrado, Tipo: " + Cliente.class.getSimpleName());
        }
        return cli;
    }
}
