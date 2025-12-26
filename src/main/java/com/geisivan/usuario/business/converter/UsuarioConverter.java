package com.geisivan.usuario.business.converter;

import com.geisivan.usuario.business.dto.EnderecoDTO;
import com.geisivan.usuario.business.dto.TelefoneDTO;
import com.geisivan.usuario.business.dto.UsuarioDTO;
import com.geisivan.usuario.infrastructure.entity.Endereco;
import com.geisivan.usuario.infrastructure.entity.Telefone;
import com.geisivan.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UsuarioConverter {

    // Converte UsuarioDTO em Usuario (entity)
    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    // Converte lista de EnderecoDTO para lista de Endereco (entity)
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){
        if (enderecoDTOS == null){
            return  List.of();
        }
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    // Converte EnderecoDTO em Endereco (entity)
    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    // Converte lista de TelefoneDTO para lista de Telefone (entity)
    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefonesDTO){
        if (telefonesDTO == null) return List.of();
        return telefonesDTO.stream().map(this::paraTelefone).toList();
    }

    // Converte TelefoneDTO em Telefone (entity)
    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    // Converte Usuario (entity) em UsuarioDTO
    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    // Converte lista de Endereco (entity) para lista de EnderecoDTO
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos){
        if (enderecos == null){
            return List.of();
        }
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    // Converte Endereco (entity) em EnderecoDTO
    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    // Converte lista de Telefone (entity) para lista de TelefoneDTO
    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefones){
        if (telefones == null){
            return List.of();
        }
        return telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    // Converte Telefone (entity) em TelefoneDTO
    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
   }

   // Atualiza dados do Usuario (entity) com valores do UsuarioDTO (update parcial)
   public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
   }

    // Atualiza dados do Endereco (entity) com valores do EnderecoDTO (update parcial)
    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : entity.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : entity.getNumero())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : entity.getCidade())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : entity.getCep())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : entity.getComplemento())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : entity.getEstado())
                .build();
    }

    // Atualiza dados do Telefone (entity) com valores do TelefoneDTO (update parcial)
    public Telefone updateTelefone(TelefoneDTO telefoneDTO, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : entity.getDdd())
                .numero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : entity.getNumero())
                .build();
    }
}
