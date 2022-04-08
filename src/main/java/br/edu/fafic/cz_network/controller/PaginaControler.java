package br.edu.fafic.cz_network.controller;


import br.edu.fafic.cz_network.model.Pagina;
import br.edu.fafic.cz_network.service.PaginaServiceIMP;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.UUID;

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
    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Pagina pagina){
        return ResponseEntity.ok().body(paginaServiceIMP.update(pagina));
    }
    @GetMapping("/search/{id}")
    public ResponseEntity search(@PathVariable UUID id){
        return ResponseEntity.ok().body(paginaServiceIMP.findById(id));
    }
    @GetMapping("/search/all")
    public ResponseEntity all(){
        return ResponseEntity.ok().body(paginaServiceIMP.listar());
    }

}
