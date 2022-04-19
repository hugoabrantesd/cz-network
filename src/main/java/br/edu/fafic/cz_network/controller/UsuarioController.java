package br.edu.fafic.cz_network.controller;

import br.edu.fafic.cz_network.model.Educacao;
import br.edu.fafic.cz_network.model.Endereco;
import br.edu.fafic.cz_network.model.InteressesPessoais;
import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.service.UsuarioService;
import br.edu.fafic.cz_network.utils.CodigosHTTP;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final String MENSAGEM_ERRO = "Erro!";

    public UsuarioController(UsuarioService userService) {
        this.usuarioService = userService;
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<Object> salvar(@RequestBody Usuario user) {
        Usuario usuario = usuarioService.salvar(user);

        if (usuario != null) {
            return ResponseEntity.status(CodigosHTTP.CREATED).body(usuario);
        }
        return ResponseEntity.badRequest().body("[]");
    }

    @DeleteMapping(value = "/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable String id) {
        String resposta = usuarioService.deletar(id);

        if (resposta.isEmpty()) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body(resposta);
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(resposta);
    }

    @GetMapping(value = "/buscar-todos")
    public ResponseEntity<List<Usuario>> buscarTodos() {
        return ResponseEntity.ok().body(usuarioService.buscarTodos());
    }

    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable String id) {

        try {
            Usuario usuario = usuarioService.buscarPorId(UUID.fromString(id));

            if (usuario != null) {
                return ResponseEntity.ok().body(usuario);
            }
            return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body("");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body("");
        }
    }

    @PatchMapping(value = "/atualizar")
    public ResponseEntity<Object> atualizar(@RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizar(usuario);
        if (usuarioAtualizado != null) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        }
        return ResponseEntity.badRequest().body("[]");
    }

    @PostMapping(value = "/interesses/adicionar/{idUsuario}")
    public ResponseEntity<Object> adicionarInteresses(
            @RequestBody LinkedTreeMap<String, List<InteressesPessoais>> interesses,
            @PathVariable UUID idUsuario) {

        Usuario usuarioAtualizado = usuarioService
                .adicionarInteresse(idUsuario, interesses.get("interessesPessoais"));
        if (usuarioAtualizado != null) {
            return ResponseEntity.status(CodigosHTTP.CREATED).body(usuarioAtualizado);
        } else {
            return ResponseEntity.badRequest().body(MENSAGEM_ERRO);
        }
    }

    @GetMapping(value = "/interesses/buscar/{idInteresse}")
    public ResponseEntity<Object> buscarInteresses(@PathVariable UUID idInteresse) {

        InteressesPessoais interesseEncontrado = usuarioService.buscarInteressesPessoais(idInteresse);
        if (interesseEncontrado != null) {
            return ResponseEntity.status(CodigosHTTP.OK).body(interesseEncontrado);
        } else {
            return ResponseEntity.badRequest().body(MENSAGEM_ERRO);
        }
    }

    @GetMapping(value = "/interesses/buscar-todos/{idUsuario}")
    public ResponseEntity<Object> buscarTodosInteresses(@PathVariable UUID idUsuario) {

        List<InteressesPessoais> interessesEncontrados = usuarioService.buscarTodosInteressesPessoais(idUsuario);
        if (interessesEncontrados != null) {
            return ResponseEntity.status(CodigosHTTP.OK).body(interessesEncontrados);
        } else {
            return ResponseEntity.badRequest().body(MENSAGEM_ERRO);
        }
    }

    @PatchMapping(value = "/interesses/atualizar/{idUsuario}")
    public ResponseEntity<Object> atualizarInteresses(
            @RequestBody InteressesPessoais interesse,
            @PathVariable UUID idUsuario) {

        Usuario usuarioAtualizado = usuarioService.atualizarInteresse(idUsuario, interesse);
        if (usuarioAtualizado != null) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        } else {
            return ResponseEntity.badRequest().body(MENSAGEM_ERRO);
        }
    }

    @DeleteMapping(value = "/interesses/deletar/{idUsuario}/{idInteresse}")
    public ResponseEntity<Object> deletarInteresse(
            @PathVariable UUID idUsuario,
            @PathVariable UUID idInteresse) {

        Usuario usuarioAtualizado = usuarioService.deletarInteresse(idUsuario, idInteresse);
        if (usuarioAtualizado != null) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        }
        return ResponseEntity.badRequest().body(MENSAGEM_ERRO);
    }

    @DeleteMapping(value = "/interesses/deletar-todos/{idUsuario}")
    public ResponseEntity<Object> deletarTodosInteresse(@PathVariable UUID idUsuario) throws InterruptedException {

        Usuario usuarioAtualizado = usuarioService.deletarTodosInteresses(idUsuario);
        if (usuarioAtualizado != null) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        }
        return ResponseEntity.badRequest().body(MENSAGEM_ERRO);
    }

    @PostMapping(value = "/educacao/adicionar/{idUsuario}")
    public ResponseEntity<Object> adicionarEducacao(
            @PathVariable UUID idUsuario, @RequestBody LinkedTreeMap<String, List<Educacao>> educacaoList) {
        Usuario usuarioComEducacaoAtualizada = usuarioService
                .adicionarEducacao(educacaoList.get("educacao"), idUsuario);

        if (usuarioComEducacaoAtualizada != null) {
            return ResponseEntity.status(CodigosHTTP.CREATED).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @GetMapping(value = "/educacao/buscar/{idEducacao}")
    public ResponseEntity<Object> buscarEducacao(@PathVariable UUID idEducacao) {
        Educacao educacao = usuarioService.buscarEducacao(idEducacao);

        if (educacao != null) {
            return ResponseEntity.status(CodigosHTTP.OK).body(educacao);
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @GetMapping(value = "/educacao/buscar-tudo/{idUsuario}")
    public ResponseEntity<Object> buscarTodaEducacao(@PathVariable UUID idUsuario) {
        List<Educacao> educacaoList = usuarioService.buscarTodaEducacao(idUsuario);

        if (educacaoList != null) {
            return ResponseEntity.status(CodigosHTTP.OK).body(educacaoList);
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @PatchMapping(value = "/educacao/atualizar/{idUsuario}")
    public ResponseEntity<Object> atualizarEducacao(
            @PathVariable UUID idUsuario, @RequestBody Educacao educacao) {

        Usuario usuarioComEducacaoAtualizada = usuarioService.atualizarEducacao(idUsuario, educacao);

        if (usuarioComEducacaoAtualizada != null) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @DeleteMapping(value = "/educacao/deletar/{idUsuario}/{idEducacao}")
    public ResponseEntity<Object> deletarEducacao(
            @PathVariable UUID idUsuario,
            @PathVariable UUID idEducacao) {

        Usuario usuarioComEducacaoAtualizada = usuarioService.deletarEducacao(idUsuario, idEducacao);

        if (usuarioComEducacaoAtualizada != null) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @DeleteMapping(value = "/educacao/deletar-toda-educacao/{idUsuario}")
    public ResponseEntity<Object> deletarTodaEducacao(@PathVariable UUID idUsuario) {
        Usuario usuarioComEducacaoAtualizada = usuarioService.deletarTodaEducacao(idUsuario);

        if (usuarioComEducacaoAtualizada != null) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @PostMapping(value = "/endereco/adicionar/{idUsuario}")
    public ResponseEntity<Object> adicionarEndereco(
            @PathVariable UUID idUsuario, @RequestBody LinkedTreeMap<String, List<Endereco>> enderecos) {

        Usuario usuarioComEnderecoAtualizado = usuarioService
                .adicionarEndereco(enderecos.get("enderecos"), idUsuario);

        if (usuarioComEnderecoAtualizado != null) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @GetMapping(value = "/endereco/buscar/{idEndereco}")
    public ResponseEntity<Object> buscarEducacao(@PathVariable UUID idEndereco) {
        Endereco enderecoEncontrado = usuarioService.buscarEndereco(idEndereco);

        if (enderecoEncontrado != null) {
            return ResponseEntity.status(CodigosHTTP.OK).body(enderecoEncontrado);
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @GetMapping(value = "/endereco/buscar-tudo/{idUsuario}")
    public ResponseEntity<Object> buscarTodosEnderecos(@PathVariable UUID idUsuario) {
        List<Endereco> enderecosEncontrados = usuarioService.buscarTodosEnderecos(idUsuario);

        if (enderecosEncontrados != null) {
            return ResponseEntity.status(CodigosHTTP.OK).body(enderecosEncontrados);
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @PatchMapping(value = "/endereco/atualizar/{idUsuario}")
    public ResponseEntity<Object> atualizarEndereco(
            @PathVariable UUID idUsuario, @RequestBody Endereco endereco) {

        boolean enderecoAtualizado = usuarioService.atualizarEndereco(idUsuario, endereco);

        if (enderecoAtualizado) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @DeleteMapping(value = "/endereco/deletar/{idUsuario}/{idEndereco}")
    public ResponseEntity<Object> deletarEndereco(
            @PathVariable UUID idUsuario, @PathVariable UUID idEndereco) {

        boolean enderecoDeletado = usuarioService.deletarEndereco(idUsuario, idEndereco);

        if (enderecoDeletado) {
            return ResponseEntity.status(CodigosHTTP.NO_CONTENT).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

}


