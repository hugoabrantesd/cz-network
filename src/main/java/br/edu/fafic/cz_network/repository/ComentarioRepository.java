package br.edu.fafic.cz_network.repository;

import br.edu.fafic.cz_network.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComentarioRepository extends JpaRepository<Comentario, UUID> {
}