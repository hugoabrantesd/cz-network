package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Postagem;
import br.edu.fafic.cz_network.model.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PostagemService{

    Postagem save(String descPostagem, Usuario usuario, MultipartFile imageFile) throws IOException;
    Postagem delete(Postagem postagem);
    Postagem update(Postagem postagem);
    Postagem findById(Long id);
    List<Postagem> listar();
}
