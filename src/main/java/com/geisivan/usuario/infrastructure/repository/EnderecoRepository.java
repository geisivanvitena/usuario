package com.geisivan.usuario.infrastructure.repository;

import com.geisivan.usuario.infrastructure.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
