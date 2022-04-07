package br.edu.fafic.cz_network.repository;

import br.edu.fafic.cz_network.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostagemRepository extends JpaRepository<Postagem, UUID> {

}
