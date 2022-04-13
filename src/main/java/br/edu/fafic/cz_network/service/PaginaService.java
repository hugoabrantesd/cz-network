package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Pagina;

import java.util.List;
import java.util.UUID;

public interface PaginaService {

   Pagina save(Pagina pagina);
   Pagina delete(Pagina pagina);
   Pagina update(Pagina pagina);
   Pagina findById(UUID id);
   List<Pagina> listar();
}
