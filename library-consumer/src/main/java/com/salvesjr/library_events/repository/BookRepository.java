package com.salvesjr.library_events.repository;

import com.salvesjr.library_events.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
