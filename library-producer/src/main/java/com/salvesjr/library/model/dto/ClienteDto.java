package com.salvesjr.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDto {

    @NotNull
    private Integer clienteId;

    private String nome;

    @NotEmpty(message="Preenchimento obrigatório")
    @Email(message = "email invalido")
    private String email;
}
