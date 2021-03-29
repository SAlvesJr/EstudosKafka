package com.libraryevents.domain;

public enum LibraryEventType {
    NEW("new"),
    UPDATE("update");

    private String descricao;

    LibraryEventType(String s) {
        this.descricao = s;
    }

    public String getDescricao() {
        return descricao;
    }
}
