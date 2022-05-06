package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.*;
import br.edu.fafic.cz_network.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final InteressesRepository interessesRepository;
    private final EducacaoRepository educacaoRepository;
    private final EnderecoRepository enderecoRepository;
    private final NotificacaoRepository notificacaoRepository;

    private final PostagemService postagemService;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          InteressesRepository interessesRepository,
                          EducacaoRepository educacaoRepository,
                          EnderecoRepository enderecoRepository,
                          NotificacaoRepository notificacaoRepository, PostagemService postagemService) {
        this.usuarioRepository = usuarioRepository;
        this.interessesRepository = interessesRepository;
        this.educacaoRepository = educacaoRepository;
        this.enderecoRepository = enderecoRepository;
        this.notificacaoRepository = notificacaoRepository;
        this.postagemService = postagemService;
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

    public Usuario adicionarInteresse(UUID idUsuario, List<InteressesPessoais> interesses) {
        Usuario usuarioEncontrado = buscarPorId(idUsuario);

        if (usuarioEncontrado != null) {
            for (InteressesPessoais interesse : interesses) {
                usuarioEncontrado.getInteressesPessoais().add(interesse);
            }
            return salvar(usuarioEncontrado);
        }
        return null;
    }

    public InteressesPessoais buscarInteressesPessoais(UUID idInteresse) {
        Optional<InteressesPessoais> interessesPessoais = interessesRepository.findById(idInteresse);
        return interessesPessoais.orElse(null);
    }

    public List<InteressesPessoais> buscarTodosInteressesPessoais(UUID idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);

        if (usuario != null) {
            return usuario.getInteressesPessoais();
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

    public Usuario adicionarEndereco(List<Endereco> enderecos, UUID idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);

        if (usuario != null) {
            for (Endereco end : enderecos) {
                usuario.getEnderecos().add(end);
            }
            return salvar(usuario);
        }
        return null;
    }

    public Endereco buscarEndereco(UUID idEndereco) {
        Optional<Endereco> endereco = enderecoRepository.findById(idEndereco);
        return endereco.orElse(null);
    }

    public List<Endereco> buscarTodosEnderecos(UUID idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);

        if (usuario != null) {
            return usuario.getEnderecos();
        }
        return null;
    }

    public boolean atualizarEndereco(UUID idUsuario, Endereco endereco) {
        Usuario usuarioEncontrado = buscarPorId(idUsuario);

        if (usuarioEncontrado != null) {

            for (Endereco end : usuarioEncontrado.getEnderecos()) {
                if (end.getId().toString().equals(endereco.getId().toString())) {
                    usuarioEncontrado.getEnderecos().remove(end);
                    usuarioEncontrado.getEnderecos().add(endereco);
                    salvar(usuarioEncontrado);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deletarEndereco(UUID idUsuario, UUID idEndereco) {
        Usuario usuarioEncontrado = buscarPorId(idUsuario);

        if (usuarioEncontrado != null && usuarioEncontrado.getEnderecos().size() > 1) {
            for (Endereco end : usuarioEncontrado.getEnderecos()) {
                if (end.getId().toString().equals(idEndereco.toString())) {
                    usuarioEncontrado.getEnderecos().remove(end);
                    salvar(usuarioEncontrado);
                    return true;
                }
            }
        }
        return false;
    }

    public Notificacao gerarNotificacao(Notificacao notificacao) {

        //System.out.println(notificacao.getAcao().getNome());

        UUID idAutor = notificacao.getIdUsuarioAutor();
        UUID idReceptor = notificacao.getIdUsuarioReceptor();
        UUID idPostagem = notificacao.getIdPostagemAcionada();

        Usuario autorEncontrado = buscarPorId(idAutor);
        Usuario receptorEncontrado = buscarPorId(idReceptor);
        Postagem postagemEncontrada = postagemService.findById(idPostagem);

        if (autorEncontrado != null && receptorEncontrado != null && postagemEncontrada != null) {
            notificacao.setDataHoraNotificacao(LocalDate.now());
            autorEncontrado.getNotificacoes().add(notificacao);
            receptorEncontrado.getNotificacoes().add(notificacao);

            return notificacaoRepository.save(notificacao);
        }
        return null;
    }

    public List<Notificacao> retornarNotificacoes(UUID idUsuario) {
        final Usuario usuario = buscarPorId(idUsuario);
        if (usuario != null) {
            return usuario.getNotificacoes();
        }
        return null;
    }

    public boolean atualizarOuDeletarNotificacao(Optional<Notificacao> novaNotificacao, boolean atualizarNotificacao) {

        List<Notificacao> notificacoesExistentes = notificacaoRepository.findAll();

        for (Notificacao notificacao : notificacoesExistentes) {
            if (novaNotificacao.isPresent() &&
                    notificacao.getId().toString().equals(novaNotificacao.get().getId().toString())) {

                final Usuario usuarioAutor = buscarPorId(notificacao.getIdUsuarioAutor());
                final Usuario usuarioReceptor = buscarPorId(notificacao.getIdUsuarioReceptor());

                usuarioAutor.getNotificacoes().remove(notificacao);
                usuarioReceptor.getNotificacoes().remove(notificacao);

                notificacaoRepository.delete(notificacao);

                if (atualizarNotificacao) {
                    notificacaoRepository.save(novaNotificacao.get());
                } else {
                    salvar(usuarioAutor);
                    salvar(usuarioReceptor);
                }
                return true;
            }
        }
        return false;
    }

    public Acao retornarAcaoNotificacao(UUID idNotificacao) {
        final Optional<Notificacao> notificacaoSelecionada = notificacaoRepository.findById(idNotificacao);

        if (notificacaoSelecionada.isPresent()) {
            final Notificacao notificacao = notificacaoSelecionada.get();
            return notificacao.getAcao();
        }
        return null;
    }

    public boolean atualizarAcaoNotificacao(UUID idNotificacao, Acao acao) {
        final Optional<Notificacao> notificacaoSelecionada = notificacaoRepository.findById(idNotificacao);

        if (notificacaoSelecionada.isPresent() && acao != null && !acao.getNomeAcao().isEmpty()) {
            final Notificacao notificacao = notificacaoSelecionada.get();
            notificacao.setAcao(acao);
            notificacaoRepository.save(notificacao);
            return true;
        }
        return false;
    }

}






