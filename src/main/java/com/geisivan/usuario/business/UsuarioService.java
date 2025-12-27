package com.geisivan.usuario.business;

import com.geisivan.usuario.business.converter.UsuarioConverter;
import com.geisivan.usuario.business.dto.EnderecoDTO;
import com.geisivan.usuario.business.dto.TelefoneDTO;
import com.geisivan.usuario.business.dto.UsuarioDTO;
import com.geisivan.usuario.infrastructure.entity.Endereco;
import com.geisivan.usuario.infrastructure.entity.Telefone;
import com.geisivan.usuario.infrastructure.entity.Usuario;
import com.geisivan.usuario.infrastructure.exceptions.ConflictException;
import com.geisivan.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.geisivan.usuario.infrastructure.repository.EnderecoRepository;
import com.geisivan.usuario.infrastructure.repository.TelefoneRepository;
import com.geisivan.usuario.infrastructure.repository.UsuarioRepository;
import com.geisivan.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
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

    public UsuarioDTO buscaUsuarioPorEmail(String email){
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Atenção: E-mail não encontrado. " + email)));

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Atenção: E-mail não encontrado. ", e.getCause());
        }
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
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);

        // Salva o usuário atualizado no banco de dados
        // Converte a entidade salva para UsuarioDTO para retorno
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO atualizaEndereco(Long endereco_id, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(endereco_id).orElseThrow(
                () -> new ResourceNotFoundException("Atenção: ID do endereço não encontrado. " + endereco_id)
        );
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long telefone_id, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(telefone_id).orElseThrow(
                () -> new ResourceNotFoundException("Atenção: ID do telefone não encontrado. " + telefone_id)
        );
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO enderecoDTO){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Atenção: E-mail não encontrado. " + email)
        );
        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(
                enderecoRepository.save(endereco));
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO telefoneDTO){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Atenção: E-mail não encontrado. " + email)
        );
        Telefone telefone = usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(
                telefoneRepository.save(telefone)
        );
    }
}
