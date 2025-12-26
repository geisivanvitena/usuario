package com.geisivan.usuario.business;

import com.geisivan.usuario.business.converter.UsuarioConverter;
import com.geisivan.usuario.business.dto.UsuarioDTO;
import com.geisivan.usuario.infrastructure.entity.Usuario;
import com.geisivan.usuario.infrastructure.exceptions.ConflictException;
import com.geisivan.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.geisivan.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void emailExiste(String email){
        try {
            boolean existe = verificaEmailExistente(email);

            if (existe){
                throw new ConflictException("Atenção: E-mail já cadastrado. " + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Atenção: E-mail já cadastrado. " + e.getCause());
        }
    }

    public Usuario buscaUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Atenção: E-mail não encontrado. " + email));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}
