package br.edu.fafic.cz_network.repository;

import br.edu.fafic.cz_network.model.Educacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EducacaoRepository extends JpaRepository<Educacao, UUID> {
}
