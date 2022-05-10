package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Comentario;

import java.util.List;
import java.util.UUID;

public interface ComentarioService {

    Comentario salvar(Comentario comentario);
    String deletar(Comentario comentario);
    Comentario atualizar(Comentario comentario);
    Comentario buscarPorId(UUID id);
    List<Comentario> buscarTodos();
}
