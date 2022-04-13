package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Interesses;
import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.repository.UsuarioRepository;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(Usuario usuario) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            usuario.setContaCriadaEm(LocalDate.now());
            String dataFormatada = formatter.format(usuario.getContaCriadaEm());
            LocalDate localDate = LocalDate.parse(dataFormatada, formatter);
            usuario.setContaCriadaEm(localDate);

            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.out.println("Erro ao salvar usuário".toUpperCase());
            e.printStackTrace();
            return null;
        }
    }

    public String deletar(String id) {
        try {
            Usuario usuario = buscarPorId(UUID.fromString(id));
            if (usuario != null) {
                usuarioRepository.delete(usuario);
                return "Deletado com sucesso!";
            }
        } catch (IllegalArgumentException e) {
            return "Id inválido!";
        }
        return "Usuário não localizado!";
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    public Usuario atualizar(Usuario usuario) {
        try {
            Usuario usuarioEncontrado = buscarPorId(usuario.getId());
            if (usuarioEncontrado != null) {
                usuario.setContaCriadaEm(usuarioEncontrado.getContaCriadaEm());
                return usuarioRepository.save(usuario);
            }
            return null;
        } catch (Exception e) {
            System.out.println("ERRO AO ATUALIZAR USUÁRIO");
            e.printStackTrace();
            return null;
        }
    }

    public Usuario criarAtualizarInteresses(UUID id, List<Interesses> interesses) {
        Usuario usuarioEncontrado = buscarPorId(id);
        if (usuarioEncontrado != null) {

            System.out.println(usuarioEncontrado.toString());

            usuarioEncontrado.setInteressesPessoais(interesses);
            salvar(usuarioEncontrado);
            return usuarioEncontrado;
        }
        return null;
    }

    public Usuario criarAtualizarInteresses(UUID id, LinkedTreeMap<String, Object> jsonInteresses) {
        List<HashMap<String, String>> dados =
                (List<HashMap<String, String>>) jsonInteresses.get("interessesPessoais");
        List<Interesses> interessesObtidos = new ArrayList<>();

        for (HashMap<String, String> interessesPessoais : dados) {
            Interesses in = Interesses.builder().build();
            in.setInteresses(interessesPessoais.get("interesses"));
            interessesObtidos.add(in);
        }

        if (!interessesObtidos.isEmpty()) {
            Usuario usuarioEncontrado = buscarPorId(id);
            if (usuarioEncontrado != null) {
                System.out.println(usuarioEncontrado.toString());

                usuarioEncontrado.setInteressesPessoais(interessesObtidos);
                salvar(usuarioEncontrado);
                return usuarioEncontrado;
            }
        }
        return null;
    }

    public boolean deletarInteresses(UUID idUsuario, Integer idInteresse) {
        Usuario usuarioEncontrado = buscarPorId(idUsuario);
        boolean interesseDeletado = false;
        if (usuarioEncontrado != null) {
            List<Interesses> interesses = usuarioEncontrado.getInteressesPessoais();

            for (Interesses in : interesses) {
                if (Objects.equals(in.getId(), idInteresse)) {
                    interesses.remove(in);
                    interesseDeletado = true;
                    break;
                }
            }
            usuarioRepository.save(usuarioEncontrado);

        }
        return interesseDeletado;
    }

    public List<Interesses> buscarTodosOsInteresses(UUID idUsuario) {
        Usuario usuarioEncontrado = buscarPorId(idUsuario);
        if (usuarioEncontrado != null) {
            return usuarioEncontrado.getInteressesPessoais();
        }
        return null;
    }
}
