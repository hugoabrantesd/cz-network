package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Pagina;
import br.edu.fafic.cz_network.repository.PaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaginaServiceIMP implements PaginaService {

    @Autowired
    PaginaRepository paginaRepository;

    @Override
    public Pagina save(Pagina pagina) {
        return paginaRepository.save(pagina);
    }

    @Override
    public Pagina delete(Pagina pagina) {
        Pagina paginaRemove = findById(pagina.getId());
        paginaRepository.delete(paginaRemove);
        return paginaRemove;
    }

    @Override
    public Pagina update(Pagina pagina) {
        Pagina paginaUpdate = findById(pagina.getId());
        if (paginaUpdate != null) {
            paginaUpdate = pagina;
            paginaRepository.save(paginaUpdate);
        }
//        paginaUpdate.setCategoria(pagina.getCategoria());
//        paginaUpdate.setFoto(pagina.getFoto());
//        paginaUpdate.setId(pagina.getId());
//        paginaUpdate.setNome(pagina.getNome());
//        paginaUpdate.setPostagens(pagina.getPostagens());
//        paginaUpdate.setSobre(pagina.getSobre());
//        paginaUpdate.setDataHoraCriacao(pagina.getDataHoraCriacao());
//        paginaUpdate.setVisibilidade(pagina.getVisibilidade());
//        paginaRepository.save(paginaUpdate);
        return paginaUpdate;
    }

    @Override
    public Pagina findById(UUID id) {
        Optional<Pagina> paginaOptional = paginaRepository.findById(id);
        return paginaOptional.orElseThrow(() -> new RuntimeException("Pagina não encontrada!!"));
    }

    @Override
    public List<Pagina> listar() {
        return paginaRepository.findAll();
    }
}
