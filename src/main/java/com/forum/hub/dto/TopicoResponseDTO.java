package com.forum.hub.dto;

import java.time.LocalDateTime;

public record TopicoResponseDTO(
        Long id,
        String titulo,
        String mensagem,
        String autor,
        LocalDateTime dataCriacao
) {
}
