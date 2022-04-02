package br.edu.fafic.cz_network.repository;

import br.edu.fafic.cz_network.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}