package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository userRepository;

    public UsuarioService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Usuario save(Usuario user) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            user.setContaCriadaEm(LocalDate.now());
            String dataFormatada = formatter.format(user.getContaCriadaEm());
            LocalDate localDate = LocalDate.parse(dataFormatada, formatter);
            user.setContaCriadaEm(localDate);

            return userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Erro ao salvar usuário".toUpperCase());
            e.printStackTrace();
            return null;
        }
    }

    public String deleteUser(String id) {

        try {
            Usuario usuario = findUser(UUID.fromString(id));
            if (usuario != null) {
                userRepository.delete(usuario);
                return "Deletado com sucesso!";
            }
        } catch (IllegalArgumentException e) {
            return "Id inválido!";
        }
        return "Usuário não localizado!";

    }

    public List<Usuario> findAllUsers() {
        return userRepository.findAll();
    }

    public Usuario findUser(UUID uuid) {
        Optional<Usuario> usuario = userRepository.findById(uuid);
        return usuario.orElse(null);
    }

}
