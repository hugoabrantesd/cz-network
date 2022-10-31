package br.edu.fafic.cz_network.controller;

import br.edu.fafic.cz_network.dto.UsuarioLoginDto;
import br.edu.fafic.cz_network.model.*;
import br.edu.fafic.cz_network.service.UsuarioService;
import br.edu.fafic.cz_network.utils.CodigosHTTP;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final String MENSAGEM_ERRO = "Erro!";

    public UsuarioController(UsuarioService userService) {
        this.usuarioService = userService;
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<Object> salvar(@RequestParam("user") Usuario user, @RequestParam("image") MultipartFile image) throws IOException {
        System.out.println(user);
        Usuario usuario = usuarioService.salvarComImagem(user, image);

        if (usuario != null) {
            return ResponseEntity.status(CodigosHTTP.CREATED).body(usuario);
        }
        return ResponseEntity.badRequest().body("[]");
    }

    @PostMapping(value = "/salvar-sem-imagem")
    public ResponseEntity<Object> salvarSemImagem(@RequestBody Usuario user) {
        System.out.println(user);
        Usuario usuario = usuarioService.salvar(user);

        if (usuario != null) {
            return ResponseEntity.status(CodigosHTTP.CREATED).body(usuario);
        }
        return ResponseEntity.badRequest().body("[]");
    }

    @PostMapping(value = "/login")
    public Usuario login(@RequestBody UsuarioLoginDto usuario) {
        return usuarioService.login(usuario);
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
    public ResponseEntity<Object> deletarTodosInteresse(@PathVariable UUID idUsuario) {

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
    public ResponseEntity<Object> buscarEndereco(@PathVariable UUID idEndereco) {
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

    @PostMapping(value = "/notificacao/gerar")
    public ResponseEntity<Object> gerarNotificacao(@RequestBody Notificacao notificacao) {

        Notificacao notificacaoGerada = usuarioService
                .gerarNotificacao(notificacao);

        if (notificacaoGerada != null) {
            return ResponseEntity.status(CodigosHTTP.CREATED).body(notificacaoGerada);
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @GetMapping(value = "/notificacao/retornar-todas/{idUsuario}")
    public ResponseEntity<Object> retornarNotificacoes(@PathVariable UUID idUsuario) {
        List<Notificacao> notificacoesEncontradas = usuarioService.retornarNotificacoes(idUsuario);

        if (notificacoesEncontradas != null) {
            return ResponseEntity.status(CodigosHTTP.OK).body(notificacoesEncontradas);
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @PatchMapping(value = "/notificacao/atualizar")
    public ResponseEntity<Object> atualizarNotificacao(@RequestBody Notificacao notificacao) {

        boolean notificacaoAtualizada = usuarioService.atualizarOuDeletarNotificacao(
                Optional.ofNullable(notificacao), true);

        if (notificacaoAtualizada) {
            return ResponseEntity.status(CodigosHTTP.OK).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @DeleteMapping(value = "/notificacao/deletar")
    public ResponseEntity<Object> deletarNotificacao(@RequestBody Notificacao notificacao) {

        boolean notificacaoDeletada = usuarioService.atualizarOuDeletarNotificacao(
                Optional.ofNullable(notificacao), false);

        if (notificacaoDeletada) {
            return ResponseEntity.status(CodigosHTTP.OK).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @GetMapping(value = "/notificacao/acao/buscar/{idNotificacao}")
    public ResponseEntity<Object> retornarAcaoNotificacao(@PathVariable UUID idNotificacao) {
        Acao acaoEncontrada = usuarioService.retornarAcaoNotificacao(idNotificacao);

        if (acaoEncontrada != null) {
            return ResponseEntity.status(CodigosHTTP.OK).body(acaoEncontrada);
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @PatchMapping(value = "/notificacao/acao/atualizar")
    public ResponseEntity<Object> atualizarAcaoNotificacao(
            @RequestBody String dados) {

        HashMap<String, Object> json = new Gson().fromJson(dados, HashMap.class);
        LinkedTreeMap<String, String> data = (LinkedTreeMap) json.get("acao");

        UUID idNotificacao = UUID.fromString(String.valueOf(json.get("idNotificacao")));
        String nomeAcao = data.get("nomeAcao");

        Acao acao = Acao.builder().nomeAcao(nomeAcao).build();

        boolean acaoAtualizada = usuarioService.atualizarAcaoNotificacao(idNotificacao, acao);

        if (acaoAtualizada) {
            return ResponseEntity.status(CodigosHTTP.OK).body("");
        }
        return ResponseEntity.status(CodigosHTTP.NOT_FOUND).body(MENSAGEM_ERRO);
    }

    @GetMapping(value = "/{nome_img}")
    public ResponseEntity<Object> getImage(@PathVariable String nome_img) throws IOException {

        var contentType = MediaType.IMAGE_JPEG;

        var filePath = "C:\\DEVELOP\\JAVA_PROJECTS\\cz-network\\src\\" +
                "main\\java\\br\\edu\\fafic\\cz_network\\imagens\\Maria Oliveira";

        final File file = new File(filePath + "\\" + nome_img);

        if (file.exists()) {
            Path path = Paths.get(filePath, nome_img);
            final byte[] imageBytes = Files.readAllBytes(path);

            if (imageBytes.length > 0) {
                final ByteArrayResource inputStream = new ByteArrayResource(imageBytes);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentLength(inputStream.contentLength())
                        .contentType(contentType)
                        .body(inputStream);
            }
        }
        System.out.println("NÃO EXISTE");
        contentType = MediaType.APPLICATION_JSON;

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(contentType)
                .body(new Gson().toJson("Imagem não encontrada!"));

    }

    /*@PostMapping(value = "/image")
    public ResponseEntity<Object> saveImage(MultipartFile imageFile, String idUsuario) throws IOException {

        // TODO: salvar imagem ao criar postagem!!!

        final Usuario usuario = usuarioService.buscarPorId(UUID.fromString(idUsuario));

        if (usuario != null) {
            final String fotoUrl = "C:\\DEVELOP\\JAVA\\cz-network\\src\\main\\java" +
                    "\\br\\edu\\fafic\\cz_network\\imagens\\"
                    + usuario.getNomeCompleto();

            File fileToSave = new File(fotoUrl);
            if (!fileToSave.exists()) {
                fileToSave.mkdir();
            }

            fileToSave = new File(fotoUrl + "\\"
                    + imageFile.getOriginalFilename());

            OutputStream os = new FileOutputStream(fileToSave);
            os.write(imageFile.getBytes());


            usuario.setUrlFoto(fotoUrl);

            return ResponseEntity.ok("Recebi");
        }
        return ResponseEntity.badRequest().build();

    }*/

}


