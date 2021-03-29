package br.com.alura.microservice.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteDto {

    private Integer clienteId;

    private String nome;

    private String email;

}
