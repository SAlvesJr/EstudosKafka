package com.salvesjr.email_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDto {
    private ClienteDto clienteDto;
    private String body;

    @Override
    public String toString() {
        return "EmailDto{" +
                "clienteDto=" + clienteDto +
                ", body='" + body + '\'' +
                '}';
    }
}
