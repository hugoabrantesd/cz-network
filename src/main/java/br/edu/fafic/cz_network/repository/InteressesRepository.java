package br.edu.fafic.cz_network.repository;

import br.edu.fafic.cz_network.model.InteressesPessoais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InteressesRepository extends JpaRepository<InteressesPessoais, UUID> {
}
