package com.forum.hub.service;

import com.forum.hub.dto.LoginRequestDTO;
import com.forum.hub.dto.UsuarioCadastroDTO;
import com.forum.hub.entity.Usuario;
import com.forum.hub.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.forum.hub.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService (
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void registrar(UsuarioCadastroDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())){
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario(
                dto.nome(),
                dto.email(),
                passwordEncoder.encode(dto.senha())
        );

        usuarioRepository.save(usuario);
    }

    public  String login(LoginRequestDTO dto){

        Usuario usuario = usuarioRepository
                .findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtService.gerarToken(usuario.getEmail());
    }


}
