package com.geisivan.usuario.business;

import com.geisivan.usuario.business.converter.UsuarioConverter;
import com.geisivan.usuario.business.dto.UsuarioDTO;
import com.geisivan.usuario.infrastructure.entity.Usuario;
import com.geisivan.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
