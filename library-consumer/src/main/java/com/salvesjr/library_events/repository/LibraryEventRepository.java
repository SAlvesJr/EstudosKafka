package com.salvesjr.library_events.repository;

import com.salvesjr.library_events.model.LibraryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryEventRepository extends JpaRepository<LibraryEvent, Integer> {
}
