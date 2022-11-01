package br.edu.fafic.cz_network.controller;


import br.edu.fafic.cz_network.model.Postagem;
import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.service.PostagemServiceIMP;
import br.edu.fafic.cz_network.service.UsuarioService;
import com.google.gson.Gson;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<Object> save(MultipartFile imageFile, Long idUsuario, String descPostagem) throws IOException {

        final Usuario usuario = usuarioService.buscarPorId(idUsuario);
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
    public ResponseEntity<Object> search(@PathVariable Long id) {
        return ResponseEntity.ok().body(postagemServiceIMP.findById(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> all() {
        return ResponseEntity.ok().body(postagemServiceIMP.listar());
    }

    @GetMapping(value = "/{id}/{nome_img}")
    public ResponseEntity<Object> getImage(@PathVariable String nome_img,
                                           @PathVariable String id) throws IOException {

        var contentType = MediaType.IMAGE_JPEG;

        var filePath = "C:\\DEVELOP\\JAVA\\cz-network\\src\\main\\java" +
                "\\br\\edu\\fafic\\cz_network\\imagens\\postagem\\" + id;

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
}
