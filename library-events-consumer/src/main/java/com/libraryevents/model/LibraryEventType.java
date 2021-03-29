package com.libraryevents.model;

public enum LibraryEventType {
    NEW("new"),
    UPDATE("update");

    private final String descricao;

    LibraryEventType(String s) {
        this.descricao = s;
    }

    public String getDescricao() {
        return descricao;
    }
}
