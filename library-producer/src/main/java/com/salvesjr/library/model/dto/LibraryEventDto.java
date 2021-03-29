package com.salvesjr.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class LibraryEventDto {

    private Integer libraryEventId;

    @NotNull(message="Preenchimento obrigatório")
    @Valid
    private ClienteDto cliente;

    @NotNull(message="Preenchimento obrigatório")
    @Valid
    private List<BookDto> book;

    private LocalDate date;
}
