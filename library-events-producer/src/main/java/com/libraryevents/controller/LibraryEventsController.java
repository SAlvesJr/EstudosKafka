package com.libraryevents.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.libraryevents.domain.LibraryEvent;
import com.libraryevents.domain.dto.LibraryEventDto;
import com.libraryevents.producer.LibraryEventProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/libraries")
public class LibraryEventsController {

    private final LibraryEventProducer libraryEventsProducer;
    String path = "id";

    public LibraryEventsController(LibraryEventProducer libraryEventsProducer) {
        this.libraryEventsProducer = libraryEventsProducer;
    }

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEventDto libraryEventDto) throws JsonProcessingException {
        var entity = libraryEventsProducer.fromEntity(libraryEventDto);
        this.libraryEventsProducer.sendLibraryEvent(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{" + path + "}").buildAndExpand(libraryEventDto.getLibraryEventId()).toUri();
        return ResponseEntity.created(uri).body(entity);
    }

    @PostMapping("/v1/librarysync")
    public ResponseEntity<LibraryEvent> postLibraryEventSync(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
        this.libraryEventsProducer.sendLibraryEventSynchronous(libraryEvent);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{" + path + "}").buildAndExpand(libraryEvent.getLibraryEventId()).toUri();
        return ResponseEntity.created(uri).body(libraryEvent);
    }

    @PostMapping("/v1/libraryapproach")
    public ResponseEntity<LibraryEvent> postLibraryEventAprroach(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException {
        this.libraryEventsProducer.sendLibraryEventApproachTwo(libraryEvent);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{" + path + "}").buildAndExpand(libraryEvent.getLibraryEventId()).toUri();
        return ResponseEntity.created(uri).body(libraryEvent);
    }

    @PutMapping("/v1/libraryevent/{id}")
    public ResponseEntity<LibraryEvent> putLibraryEvent(@RequestBody @Valid LibraryEventDto libraryEventDto, @PathVariable("id") Integer id) throws JsonProcessingException {
        var entity = libraryEventsProducer.validEntity(libraryEventDto, id);
        libraryEventsProducer.sendLibraryEventApproachTwo(entity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
