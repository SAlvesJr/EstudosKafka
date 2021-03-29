package com.salvesjr.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDto {

    private ClienteDto clienteDto;

    private String body;

    private LocalDate date;
}
