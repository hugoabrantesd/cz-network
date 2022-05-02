package br.edu.fafic.cz_network.repository;

import br.edu.fafic.cz_network.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificacaoRepository extends JpaRepository<Notificacao, UUID> {
}
