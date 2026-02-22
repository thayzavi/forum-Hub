package com.forum.hub.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicoRequestDTO(

        @NotBlank
        String titulo,

        @NotBlank
        String mensagem
) {}
