package com.salvesjr.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

    @NotNull
    private Integer bookId;

    private String bookName;

    private String bookAuthor;

    private BookStatus bookStatus;
}
