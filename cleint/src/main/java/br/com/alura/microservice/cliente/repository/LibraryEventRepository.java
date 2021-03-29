package br.com.alura.microservice.cliente.repository;


import br.com.alura.microservice.cliente.model.LibraryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryEventRepository extends JpaRepository<LibraryEvent, Integer> {
}
