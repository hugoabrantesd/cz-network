package br.edu.fafic.cz_network.service;


import br.edu.fafic.cz_network.model.Comentario;

import br.edu.fafic.cz_network.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComentarioServiceIMP implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;

    @Override
    public Comentario salvar(Comentario comentario) {
        try {
            comentario.setDataHoraComentario(LocalDateTime.now());
            return comentarioRepository.save(comentario);
        } catch (Exception e) {
            System.out.println("Erro ao salvar usuário".toUpperCase());
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String deletar(Comentario comentario) {
        try {
            Comentario removerComentario = buscarPorId(comentario.getId());
            if (comentario != null) {
                comentarioRepository.delete(removerComentario);
                return "Deletado com sucesso!";
            }
        } catch (IllegalArgumentException e) {
            return "Id inválido!";
        }
        return "Usuário não localizado!";
    }


    @Override
    public Comentario atualizar(Comentario comentario) {
        try {
            Comentario atualiarComentario = buscarPorId(comentario.getId());
            if (atualiarComentario != null) {
                atualiarComentario = comentario;
                comentarioRepository.save(atualiarComentario);
                return atualiarComentario;
            }
            return null;
        } catch (Exception e) {
            System.out.println("ERRO AO ATUALIZAR COMENTÁRIO");
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Comentario buscarPorId(Long id) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
        return comentarioOptional.orElseThrow(() -> new RuntimeException("Comentario não encontrado"));
    }

    @Override
    public List<Comentario> buscarTodos() {
        return comentarioRepository.findAll();
    }
}
