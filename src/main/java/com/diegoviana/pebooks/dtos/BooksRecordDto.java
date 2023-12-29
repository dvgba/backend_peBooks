package com.diegoviana.pebooks.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BooksRecordDto(
        @NotBlank String titulo,
        @NotBlank String autor,
        @NotBlank String editora,
        @NotBlank String capa,
        @NotNull int ano,
        @NotBlank String isbn) {
}
