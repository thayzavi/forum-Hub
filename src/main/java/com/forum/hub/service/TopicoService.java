package com.forum.hub.service;

import com.forum.hub.dto.TopicoRequestDTO;
import com.forum.hub.dto.TopicoResponseDTO;
import com.forum.hub.entity.Topico;
import com.forum.hub.entity.Usuario;
import com.forum.hub.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.forum.hub.repository.TopicoRepository;

import java.util.List;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    public TopicoService(
            TopicoRepository topicoRepository,
            UsuarioRepository usuarioRepository)
    {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }
     public void criar(TopicoRequestDTO dto) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario autor = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

         Topico topico = new Topico(
                 dto.titulo(),
                 dto.mensagem(),
                 autor
         );

         topicoRepository.save(topico);
     }

     public List<TopicoResponseDTO> listar() {
         return topicoRepository.findAll()
                 .stream()
                 .map(this::toResponseDTO)
                 .toList();
     }
     public TopicoResponseDTO buscarPorId(Long id){

         Topico topico = topicoRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

         return toResponseDTO (topico);
     }

    public void atualizar(Long id, TopicoRequestDTO dto) {

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        Usuario usuarioLogado = getUsuarioAutenticado();

        if (!topico.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new RuntimeException("Você não é o autor deste tópico");
        }

        topico.setTitulo(dto.titulo());
        topico.setMensagem(dto.mensagem());

        topicoRepository.save(topico);
    }

    public void deletar(Long id) {

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        Usuario usuarioLogado = getUsuarioAutenticado();

        if (!topico.getAutor().getId().equals(usuarioLogado.getId())) {
            throw new RuntimeException("Você não é o autor deste tópico");
        }

        topicoRepository.delete(topico);
    }

    private Usuario getUsuarioAutenticado() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado"));
    }


    private TopicoResponseDTO toResponseDTO(Topico topico) {
        return new TopicoResponseDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor().getNome(),
                topico.getDataCriacao()
        );
    }

}
