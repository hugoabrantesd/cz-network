package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Educacao;
import br.edu.fafic.cz_network.model.InteressesPessoais;
import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.repository.EducacaoRepository;
import br.edu.fafic.cz_network.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EducacaoRepository educacaoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, EducacaoRepository educacaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.educacaoRepository = educacaoRepository;
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
                return "";
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

    public Usuario criarInteresse(UUID idUsuario, List<InteressesPessoais> interesses) {
        Usuario usuarioEncontrado = buscarPorId(idUsuario);

        if (usuarioEncontrado != null) {
            for (InteressesPessoais interesse : interesses) {
                usuarioEncontrado.getInteressesPessoais().add(interesse);
            }
            return salvar(usuarioEncontrado);
        }
        return null;
    }

    public Usuario atualizarInteresse(UUID idUsuario, InteressesPessoais interesse) {
        Usuario usuarioEncontrado = buscarPorId(idUsuario);

        if (usuarioEncontrado != null) {
            for (InteressesPessoais in : usuarioEncontrado.getInteressesPessoais()) {
                if (in.getId().toString().toUpperCase(Locale.ROOT)
                        .equals(interesse.getId().toString().toUpperCase(Locale.ROOT))) {

                    usuarioEncontrado.getInteressesPessoais().remove(in);
                    usuarioEncontrado.getInteressesPessoais().add(interesse);
                    return salvar(usuarioEncontrado);
                }
            }
        }
        return null;
    }

    public Usuario deletarInteresse(UUID idUsuario, UUID idInteresse) {
        Usuario usuarioEncontrado = buscarPorId(idUsuario);

        if (usuarioEncontrado != null) {
            for (InteressesPessoais in : usuarioEncontrado.getInteressesPessoais()) {
                if (Objects.equals(in.getId(), idInteresse)) {
                    usuarioEncontrado.getInteressesPessoais().remove(in);
                    return usuarioRepository.save(usuarioEncontrado);
                }
            }
        }
        return null;
    }

    public Usuario deletarTodosInteresses(UUID idUsuario) {
        final Usuario usuario = buscarPorId(idUsuario);

        if (usuario != null && !usuario.getInteressesPessoais().isEmpty()) {
            usuario.getInteressesPessoais().clear();
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public Usuario adicionarEducacao(List<Educacao> educacaoList, UUID idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);

        if (usuario != null) {
            for (Educacao educacao : educacaoList) {
                usuario.getEducacao().add(educacao);
            }
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public Educacao buscarEducacao(UUID idEducacao) {
        Optional<Educacao> educacao = educacaoRepository.findById(idEducacao);
        return educacao.orElse(null);
    }

    public List<Educacao> buscarTodaEducacao(UUID idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);

        if (usuario != null) {
            return usuario.getEducacao();
        }
        return null;
    }

    public Usuario atualizarEducacao(UUID idUsuario, Educacao educacao) {
        final Usuario usuario = buscarPorId(idUsuario);

        if (usuario != null) {
            for (Educacao edu : usuario.getEducacao()) {
                if (edu.getId().toString().toUpperCase(Locale.ROOT)
                        .equals(educacao.getId().toString().toUpperCase(Locale.ROOT))) {

                    usuario.getEducacao().remove(edu);
                    usuario.getEducacao().add(educacao);
                    return usuarioRepository.save(usuario);
                }
            }
        }
        return null;
    }

    public Usuario deletarEducacao(UUID idUsuario, UUID idEducacao) {
        final Usuario usuario = buscarPorId(idUsuario);

        if (usuario != null) {
            for (Educacao edu : usuario.getEducacao()) {
                if (edu.getId().toString().toUpperCase(Locale.ROOT)
                        .equals(idEducacao.toString().toUpperCase(Locale.ROOT))) {
                    usuario.getEducacao().remove(edu);
                    return usuarioRepository.save(usuario);
                }
            }
        }
        return null;
    }

    public Usuario deletarTodaEducacao(UUID idUsuario) {
        final Usuario usuario = buscarPorId(idUsuario);

        if (usuario != null && !usuario.getEducacao().isEmpty()) {
            usuario.getEducacao().clear();
            return usuarioRepository.save(usuario);
        }
        return null;
    }
}






