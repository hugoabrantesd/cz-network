package br.edu.fafic.cz_network.controller;


import br.edu.fafic.cz_network.model.Pagina;
import br.edu.fafic.cz_network.model.Postagem;
import br.edu.fafic.cz_network.service.PaginaServiceIMP;
import br.edu.fafic.cz_network.service.PostagemServiceIMP;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/postagem")
public class PostagemControler {

    private final PostagemServiceIMP postagemServiceIMP;

    public PostagemControler(PostagemServiceIMP postagemServiceIMP) {
        this.postagemServiceIMP = postagemServiceIMP;
    }
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Postagem postagem){
        return ResponseEntity.ok().body(postagemServiceIMP.save(postagem));
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody Postagem postagem){
        postagemServiceIMP.delete(postagem);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Postagem postagem){
        return ResponseEntity.ok().body(postagemServiceIMP.update(postagem));
    }
    @GetMapping("/search/{id}")
    public ResponseEntity search(@PathVariable UUID id){
        return ResponseEntity.ok().body(postagemServiceIMP.findById(id));
    }
    @GetMapping("/search/all")
    public ResponseEntity all(){
        return ResponseEntity.ok().body(postagemServiceIMP.listar());
    }
}
