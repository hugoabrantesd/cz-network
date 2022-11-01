package br.edu.fafic.cz_network.controller;

import br.edu.fafic.cz_network.model.Comentario;
import br.edu.fafic.cz_network.service.ComentarioServiceIMP;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    private final ComentarioServiceIMP comentarioServiceIMP;

    public ComentarioController(ComentarioServiceIMP comentarioServiceIMP) {
        this.comentarioServiceIMP = comentarioServiceIMP;
    }

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody Comentario comentario){
        return ResponseEntity.ok().body(comentarioServiceIMP.salvar(comentario));
    }
    @DeleteMapping("/deletar")
    public ResponseEntity deletar(@RequestBody Comentario comentario){
        comentarioServiceIMP.deletar(comentario);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/atualizar")
    public ResponseEntity atualizar(@RequestBody Comentario comentario){
        return ResponseEntity.ok().body(comentarioServiceIMP.atualizar(comentario));
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity buscarPoId(@PathVariable Long id){
        return ResponseEntity.ok().body(comentarioServiceIMP.buscarPorId(id));
    }
    @GetMapping("/buscar-todos")
    public ResponseEntity buscarTodos(){
        return ResponseEntity.ok().body(comentarioServiceIMP.buscarTodos());
    }

}