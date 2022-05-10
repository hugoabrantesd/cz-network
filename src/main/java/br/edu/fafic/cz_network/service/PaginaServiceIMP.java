package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Categoria;
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
    public Pagina save(Pagina pagina, Categoria categoria) {
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

    @Override
    public Pagina deletarCategoria(UUID idPagina) {
        Pagina pagina = findById(idPagina);
        if(pagina != null){
            pagina.setCategoria(null);
            paginaRepository.save(pagina);
        }
        return pagina;

    }
}
