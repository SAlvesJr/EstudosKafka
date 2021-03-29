package br.com.alura.microservice.cliente.repository;

import br.com.alura.microservice.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Cliente findByEmail(String email);
}
