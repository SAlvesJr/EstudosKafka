package com.libraryevents.domain.dto;

import com.libraryevents.domain.Book;

public class LibraryEventDto {

    private Integer libraryEventId;

    private String libraryEventType;

    private Book book;

    public LibraryEventDto() {

    }

    public LibraryEventDto(Integer libraryEventId, String libraryEventType, Book book) {
        this.libraryEventId = libraryEventId;
        this.libraryEventType = libraryEventType;
        this.book = book;
    }

    public Integer getLibraryEventId() {
        return libraryEventId;
    }

    public void setLibraryEventId(Integer libraryEventId) {
        this.libraryEventId = libraryEventId;
    }

    public String getLibraryEventType() {
        return libraryEventType;
    }

    public void setLibraryEventType(String libraryEventType) {
        this.libraryEventType = libraryEventType;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
