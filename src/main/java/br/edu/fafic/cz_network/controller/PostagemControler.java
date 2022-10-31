package br.edu.fafic.cz_network.controller;


import br.edu.fafic.cz_network.model.Postagem;
import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.service.PostagemServiceIMP;
import br.edu.fafic.cz_network.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/postagem")
public class PostagemControler {

    private final PostagemServiceIMP postagemServiceIMP;
    private final UsuarioService usuarioService;

    public PostagemControler(PostagemServiceIMP postagemServiceIMP, UsuarioService usuarioService) {
        this.postagemServiceIMP = postagemServiceIMP;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(MultipartFile imageFile, String idUsuario, String descPostagem) throws IOException {

        final Usuario usuario = usuarioService.buscarPorId(UUID.fromString(idUsuario));
        System.out.println(descPostagem);

        if (usuario != null) {
            Postagem post = postagemServiceIMP.save(descPostagem, usuario, imageFile);
            if (post != null) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody Postagem postagem) {
        postagemServiceIMP.delete(postagem);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Postagem postagem) {
        return ResponseEntity.ok().body(postagemServiceIMP.update(postagem));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> search(@PathVariable UUID id) {
        return ResponseEntity.ok().body(postagemServiceIMP.findById(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> all() {
        return ResponseEntity.ok().body(postagemServiceIMP.listar());
    }
}
