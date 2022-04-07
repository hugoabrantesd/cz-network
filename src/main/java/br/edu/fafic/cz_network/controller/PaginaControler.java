package br.edu.fafic.cz_network.controller;


import br.edu.fafic.cz_network.model.Pagina;
import br.edu.fafic.cz_network.service.PaginaServiceIMP;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;

@RestController
@RequestMapping("/pagina")
public class PaginaControler {

    private final PaginaServiceIMP paginaServiceIMP;

    public PaginaControler(PaginaServiceIMP paginaServiceIMP) {
        this.paginaServiceIMP = paginaServiceIMP;
    }
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Pagina pagina){
        return ResponseEntity.ok().body(paginaServiceIMP.save(pagina));
    }
    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody Pagina pagina){
        paginaServiceIMP.delete(pagina);
        return ResponseEntity.noContent().build();
    }

}
