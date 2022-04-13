package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Postagem;

import java.util.List;
import java.util.UUID;

public interface PostagemService{

    Postagem save(Postagem postagem);
    Postagem delete(Postagem postagem);
    Postagem update(Postagem postagem);
    Postagem findById(UUID id);
    List<Postagem> listar();
}
