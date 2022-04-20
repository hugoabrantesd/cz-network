package br.edu.fafic.cz_network.repository;

import br.edu.fafic.cz_network.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
}
