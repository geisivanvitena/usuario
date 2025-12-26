package com.geisivan.usuario.business;

import com.geisivan.usuario.business.converter.UsuarioConverter;
import com.geisivan.usuario.business.dto.UsuarioDTO;
import com.geisivan.usuario.infrastructure.entity.Usuario;
import com.geisivan.usuario.infrastructure.exceptions.ConflictException;
import com.geisivan.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.geisivan.usuario.infrastructure.repository.UsuarioRepository;
import com.geisivan.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){

        // Remove o prefixo "Bearer " do token e extrai o e-mail (username) do JWT
        String email = jwtUtil.extractUsername(token.substring(7));

        // Criptografia de senha
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null );

        // Busca o usuário na base de dados pelo e-mail extraído do token
        // Caso não exista, lança exceção de recurso não encontrado
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Atenção: E-mail não encontrado. " + email)
        );

        // Cria um novo objeto Usuario com os dados atualizados (update parcial),
        // Mantém os dados existentes quando o DTO possui valores nulos
        Usuario usuario = usuarioConverter.paraUpdateUsuario(usuarioDTO, usuarioEntity);

        // Salva o usuário atualizado no banco de dados
        // Converte a entidade salva para UsuarioDTO para retorno
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }
}
