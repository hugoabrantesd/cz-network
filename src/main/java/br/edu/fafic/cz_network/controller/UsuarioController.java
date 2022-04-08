package br.edu.fafic.cz_network.controller;

import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

}
