package br.edu.fafic.cz_network.controller;

import br.edu.fafic.cz_network.model.Interesses;
import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.service.UsuarioService;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService userService) {
        this.usuarioService = userService;
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<Object> salvar(@RequestBody Usuario user) {
        Usuario usuario = usuarioService.salvar(user);

        if (usuario != null) {
            return ResponseEntity.ok().body(usuario);
        }
        return ResponseEntity.badRequest().body("[]");
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable String id) {
        return ResponseEntity.ok().body(usuarioService.deletar(id));
    }

    @GetMapping(value = "/buscar-todos")
    public ResponseEntity<List<Usuario>> buscarTodos() {
        return ResponseEntity.ok().body(usuarioService.buscarTodos());
    }

    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable String id) {

        try {
            Usuario usuario = usuarioService.buscarPorId(UUID.fromString(id));
            return ResponseEntity.ok().body(Objects.requireNonNullElse(usuario, "[]"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body("Id inválido!");
        }
    }

    @PatchMapping(value = "/atualizar")
    public ResponseEntity<Object> atualizar(@RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizar(usuario);
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok().body(usuarioAtualizado);
        }
        return ResponseEntity.badRequest().body("[]");
    }

    @PatchMapping(value = "/interesses/atualizar-interesses/{id}")
    public ResponseEntity<Object> atualizarInteresses(
            @RequestBody LinkedTreeMap<String, Object> interesses, @PathVariable UUID id) {

        Usuario usuarioAtualizado = usuarioService.criarOuAtualizarInteresses(id, interesses);
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok().body(usuarioAtualizado);
        } else {
            return ResponseEntity.badRequest().body("Ocorreu um erro!");
        }
    }

    @DeleteMapping(value = "/interesses/deletar-interesse/{idUsuario}/{idInteresse}")
    public ResponseEntity<Object> deletarInteresse(
            @PathVariable UUID idUsuario, @PathVariable Integer idInteresse) {

        boolean interesseDeletado = usuarioService.deletarInteresses(idUsuario, idInteresse);
        if (interesseDeletado) {
            return ResponseEntity.ok().body("Interesse deletado com sucesso!");
        }
        return ResponseEntity.badRequest().body("Ocorreu um erro!");

    }

    @GetMapping(value = "/interesses/buscar-todos/{idUsuario}")
    public ResponseEntity<Object> buscarInteresses(@PathVariable UUID idUsuario) {
        List<Interesses> interesses = usuarioService.buscarTodosOsInteresses(idUsuario);

        if (interesses != null) {
            return ResponseEntity.ok().body(interesses);
        }
        return ResponseEntity.ok().body("Ocorreu um erro!");
    }

}


