package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Postagem;
import br.edu.fafic.cz_network.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class PostagemServiceIMP implements PostagemService{

    @Autowired
    PostagemRepository postagemRepository;

    @Override
    public Postagem save(Postagem postagem) {
        return postagemRepository.save(postagem);
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
        if(postagemUpdate != null){
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
