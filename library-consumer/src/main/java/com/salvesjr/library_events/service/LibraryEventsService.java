package com.salvesjr.library_events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.salvesjr.library_events.exceptions.BookStatusRentedException;
import com.salvesjr.library_events.exceptions.ObjectNotFoundException;
import com.salvesjr.library_events.model.Book;
import com.salvesjr.library_events.model.BookStatus;
import com.salvesjr.library_events.model.LibraryEvent;
import com.salvesjr.library_events.repository.BookRepository;
import com.salvesjr.library_events.repository.LibraryEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class LibraryEventsService {

    private final ModelMapper modelMapper;
    private final LibraryEventRepository libraryEventsRepository;
    private final BookRepository bookRepository;

    public LibraryEventsService(ModelMapper modelMapper, LibraryEventRepository libraryEventRepository,
                                BookRepository bookRepository) {
        this.modelMapper = modelMapper;
        this.libraryEventsRepository = libraryEventRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Método que processar o a informação passado pelo tópico.
     *
     * @param consumerRecord
     * @throws JsonProcessingException
     */
    public void processTopicLibraryEvent(ConsumerRecord<Integer, GenericRecord> consumerRecord) throws IOException {
        var obj = modelMapper.map(consumerRecord.value(), LibraryEvent.class);
        log.info("libraryEvent : {} ", obj);
        saveLibraryEvent(obj);
    }

    /**
     * Método que salvar o evento de relação do cliente com o livro alugado.
     *
     * @param libraryEvent
     */
    private void saveLibraryEvent(LibraryEvent libraryEvent) {
        libraryEvent.getBook().forEach(b -> {
            var book = findBookForStatus(b);
            book.setBookStatus(BookStatus.RENTED);
            bookRepository.save(book);
        });
        libraryEvent.setLibraryEventId(null);
        libraryEventsRepository.save(libraryEvent);
        log.info("Successfully Persisted the library Event {}", libraryEvent);
    }

    /**
     * Método que busca o livro e valida o status.
     * - o status deve ser available para um possível aluguel
     *
     * @param book
     * @return
     */
    private Book findBookForStatus(Book book) {
        if (book == null) {
            throw new ObjectNotFoundException("Objeto não encontrado!");
        }
        var findBook = bookRepository.findById(book.getBookId());

        return findBook.filter(s -> s.getBookStatus().equals(BookStatus.AVAILABLE))
                .orElseThrow(() -> new BookStatusRentedException("O livro ja esta alugado! Id: "
                        + book.getBookId() + ", Tipo: "
                        + Book.class.getSimpleName()));
    }

}
