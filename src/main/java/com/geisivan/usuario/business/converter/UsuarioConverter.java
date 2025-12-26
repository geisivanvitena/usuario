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
        if (enderecoDTOS == null) return  List.of();
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
    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS){
        if (telefoneDTOS == null) return List.of();
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    // Converte TelefoneDTO em Telefone (entity)
    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    // Converte Usuario (entity) em UsuarioDTO
    public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO){
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefoneDTO(usuarioDTO.getTelefones()))
                .build();
    }

    // Converte lista de Endereco (entity) para lista de EnderecoDTO
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS){
        if (enderecoDTOS == null) return List.of();
        return enderecoDTOS.stream().map(this::paraEnderecoDTO).toList();
    }

    // Converte Endereco (entity) em EnderecoDTO
    public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO){
        return EnderecoDTO.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    // Converte lista de Telefone (entity) para lista de TelefoneDTO
    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTOS){
        if (telefoneDTOS == null) return List.of();
        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    // Converte Telefone (entity) em TelefoneDTO
    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO){
        return TelefoneDTO.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
   }

   // Atualiza dados do Usuario (entity) com valores do UsuarioDTO (update parcial)
   public Usuario paraUpdateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
   }
}
