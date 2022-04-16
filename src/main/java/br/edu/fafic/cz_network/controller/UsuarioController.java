package br.edu.fafic.cz_network.controller;

import br.edu.fafic.cz_network.model.Educacao;
import br.edu.fafic.cz_network.model.InteressesPessoais;
import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.service.UsuarioService;
import com.google.gson.internal.LinkedTreeMap;
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

    @PostMapping(value = "/interesses/add/{idUsuario}")
    public ResponseEntity<Object> addInteresses(
            @RequestBody LinkedTreeMap<String, List<InteressesPessoais>> interesses, @PathVariable UUID idUsuario) {

        Usuario usuarioAtualizado = usuarioService
                .addInteresses(idUsuario, interesses.get("interessesPessoais"));
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok().body(usuarioAtualizado);
        } else {
            return ResponseEntity.badRequest().body("Ocorreu um erro!");
        }
    }

    @PatchMapping(value = "/interesses/atualizar/{idUsuario}")
    public ResponseEntity<Object> atualizarInteresses(
            @RequestBody InteressesPessoais interesse,
            @PathVariable UUID idUsuario) {

        Usuario usuarioAtualizado = usuarioService.atualizarInteresse(idUsuario, interesse);
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok().body(usuarioAtualizado);
        } else {
            return ResponseEntity.badRequest().body("Ocorreu um erro!");
        }
    }

    @DeleteMapping(value = "/interesses/deletar/{idUsuario}/{idInteresse}")
    public ResponseEntity<Object> deletarInteresse(
            @PathVariable UUID idUsuario,
            @PathVariable UUID idInteresse) {

        Usuario usuarioAtualizado = usuarioService.deletarInteresse(idUsuario, idInteresse);
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok().body(usuarioAtualizado);
        }
        return ResponseEntity.badRequest().body("Ocorreu um erro!");
    }

    @DeleteMapping(value = "/interesses/deletar-todos/{idUsuario}")
    public ResponseEntity<Object> deletarTodosInteresse(@PathVariable UUID idUsuario) throws InterruptedException {

        Usuario usuarioAtualizado = usuarioService.deletarTodosInteresse(idUsuario);
        if (usuarioAtualizado != null) {
            return ResponseEntity.ok().body(usuarioAtualizado);
        }
        return ResponseEntity.badRequest().body("Ocorreu um erro!");
    }

    @PostMapping(value = "/educacao/add/{idUsuario}")
    public ResponseEntity<Object> criarOuAtualizarEducacao(
            @PathVariable UUID idUsuario, @RequestBody LinkedTreeMap<String, Object> educacao) {
        Usuario usuarioComEducacaoAtualizada = usuarioService.addEducacao(educacao, idUsuario);
        return ResponseEntity.ok().body(Objects.requireNonNullElse(
                usuarioComEducacaoAtualizada, "Ocorreu um erro!"));
    }

    @PatchMapping(value = "/educacao/atualizar/{idUsuario}")
    public ResponseEntity<Object> atualizarEducacao(
            @PathVariable UUID idUsuario, @RequestBody Educacao educacao) {

        Usuario usuarioComEducacaoAtualizada = usuarioService.atualizarEducacao(idUsuario, educacao);

        return ResponseEntity.ok().body(Objects.requireNonNullElse(
                usuarioComEducacaoAtualizada, "Ocorreu um erro!"));
    }

    @DeleteMapping(value = "/educacao/deletar/{idUsuario}/{idEducacao}")
    public ResponseEntity<Object> deletarEducacao(
            @PathVariable UUID idUsuario, @PathVariable UUID idEducacao) {
        Usuario usuarioComEducacaoAtualizada = usuarioService.deletarEducacao(idEducacao, idUsuario);
        return ResponseEntity.ok().body(Objects.requireNonNullElse(
                usuarioComEducacaoAtualizada, "Ocorreu um erro!"));
    }

    @DeleteMapping(value = "/educacao/deletar-toda-educacao/{idUsuario}")
    public ResponseEntity<Object> deletarTodaEducacao(@PathVariable UUID idUsuario) {
        Usuario usuarioComEducacaoAtualizada = usuarioService.deletarTodaEducacao(idUsuario);
        return ResponseEntity.ok().body(Objects.requireNonNullElse(
                usuarioComEducacaoAtualizada, "Ocorreu um erro!"));
    }

}


