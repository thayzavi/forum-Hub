package com.forum.hub.controller;

import com.forum.hub.dto.TopicoRequestDTO;
import com.forum.hub.entity.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.forum.hub.service.TopicoService;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoControlle {

    private final TopicoService topicoService;

    public TopicoControlle(TopicoService topicoService){
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody TopicoRequestDTO dto) {
        topicoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<?>> listar() {
        return ResponseEntity.ok(topicoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody TopicoRequestDTO dto
    ) {
        topicoService.atualizar(id, dto);
        return ResponseEntity.ok("Tópico atualizado com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @PathVariable Long id
    ) {
        topicoService.deletar(id);
        return ResponseEntity.ok("Tópico removido com sucesso");
    }
}
