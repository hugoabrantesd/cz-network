package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Postagem;
import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.repository.PostagemRepository;
import br.edu.fafic.cz_network.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class PostagemServiceIMP implements PostagemService {

    final
    PostagemRepository postagemRepository;

    private final UsuarioRepository usuarioRepository;

    public PostagemServiceIMP(PostagemRepository postagemRepository, UsuarioRepository usuarioRepository) {
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Postagem save(Postagem postagem, UUID idUsuario) {
        final Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);

        if (usuario.isPresent()) {
            Usuario usuarioEncontrado = usuario.get();
            usuarioEncontrado.getPostagens().add(postagem);

            return postagemRepository.save(postagem);
        }

        return null;
    }

    @Override
    public Postagem delete(Postagem postagem) {
        Postagem postagemRemove = findById(postagem.getId());
        postagemRepository.delete(postagemRemove);
        return postagemRemove;
    }

    @Override
    public Postagem update(Postagem postagem) {
        Postagem postagemUpdate = findById(postagem.getId());
        if (postagemUpdate != null) {
            postagemUpdate = postagem;
            postagemRepository.save(postagemUpdate);
        }
        return postagemUpdate;
    }

    @Override
    public Postagem findById(UUID id) {
        Optional<Postagem> postagemOptional = postagemRepository.findById(id);
        return postagemOptional.orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
    }

    @Override
    public List<Postagem> listar() {
        return postagemRepository.findAll();
    }
}
