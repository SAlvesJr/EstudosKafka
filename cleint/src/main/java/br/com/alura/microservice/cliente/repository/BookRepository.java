package br.com.alura.microservice.cliente.repository;


import br.com.alura.microservice.cliente.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
