package com.salvesjr.library.controller;

import com.salvesjr.library.model.dto.ClienteDto;
import com.salvesjr.library.model.dto.LibraryEventDto;
import com.salvesjr.library.producer.LibraryEventProducer;
import com.salvesjr.library.producer.SendEmailProducer;
import com.salvesjr.library.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/libraries")
public class LibraryController {

    private final LibraryEventProducer libraryEventsProducer;
    private final SendEmailProducer sendEmailProducer;
    private final ClienteService clienteService;

    public LibraryController(LibraryEventProducer libraryEventsProducer,
                             SendEmailProducer sendEmailProducer, ClienteService clienteService) {

        this.libraryEventsProducer = libraryEventsProducer;
        this.sendEmailProducer = sendEmailProducer;
        this.clienteService = clienteService;
    }

    @PostMapping("/libraryevent")
    public ResponseEntity<LibraryEventDto> postLibraryEvent(@Valid @RequestBody LibraryEventDto libraryEventDto) {
        this.libraryEventsProducer.sendTopicLibraryEvent(libraryEventDto);
        this.sendEmailProducer.createEmailToSend(libraryEventDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cliente/{email}")
    public ResponseEntity<ClienteDto> getCliente(@PathVariable String email) {
        return ResponseEntity.ok().body(this.clienteService.buscaClienteByEmail(email));
    }

}
