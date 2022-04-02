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

    private final UsuarioService userService;

    public UsuarioController(UsuarioService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<Object> salvar(@RequestBody Usuario user) {
        Usuario usuario = userService.save(user);

        if (usuario != null) {
            return ResponseEntity.ok().body(usuario);
        }
        return ResponseEntity.badRequest().body("[]");
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<String> deletxar(@PathVariable String id) {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }

    @GetMapping(value = "/buscarTodos")
    public ResponseEntity<List<Usuario>> buscarTodos() {
        return ResponseEntity.ok().body(userService.findAllUsers());
    }

    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Object> buscar(@PathVariable String id) {

        try {
            Usuario usuario = userService.findUser(UUID.fromString(id));
            return ResponseEntity.ok().body(Objects.requireNonNullElse(usuario, "[]"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body("Id inválido!");
        }
    }

}
