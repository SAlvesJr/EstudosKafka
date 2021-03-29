package com.salvesjr.library.model.dto;

public enum BookStatus {

    AVAILABLE("disponivel"),
    RENTED("alugado");

    private String descricao;

    BookStatus(String s) {
        this.descricao = s;
    }

    public String getDescricao() {
        return descricao;
    }
}
