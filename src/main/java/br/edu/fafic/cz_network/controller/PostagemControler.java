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
    @PostMapping("/save/{id-usuario}")
    public ResponseEntity save(@RequestBody Postagem postagem, @PathVariable(value = "id-usuario") UUID idUsuario){
        return ResponseEntity.ok().body(postagemServiceIMP.save(postagem, idUsuario));
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Postagem postagem){
        postagemServiceIMP.delete(postagem);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/update")
    public ResponseEntity update(@RequestBody Postagem postagem){
        return ResponseEntity.ok().body(postagemServiceIMP.update(postagem));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity search(@PathVariable UUID id){
        return ResponseEntity.ok().body(postagemServiceIMP.findById(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity all(){
        return ResponseEntity.ok().body(postagemServiceIMP.listar());
    }
}
