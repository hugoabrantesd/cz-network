package br.edu.fafic.cz_network.repository;

import br.edu.fafic.cz_network.model.Pagina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaginaRepository extends JpaRepository<Pagina, UUID> {

}