package br.edu.fafic.cz_network.service;

import br.edu.fafic.cz_network.model.Postagem;
import br.edu.fafic.cz_network.model.Usuario;
import br.edu.fafic.cz_network.repository.PostagemRepository;
import br.edu.fafic.cz_network.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class PostagemServiceIMP implements PostagemService {

    final
    PostagemRepository postagemRepository;

    private final UsuarioRepository usuarioRepository;

    public PostagemServiceIMP(PostagemRepository postagemRepository, UsuarioRepository usuarioRepository) {
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Postagem save(String descPostagem, Usuario usuario, MultipartFile imageFile) throws IOException {

        final String fotoUrl = "C:\\DEVELOP\\JAVA_PROJECTS\\" +
                "cz-network\\src\\main\\java\\br\\edu\\fafic\\cz_network\\imagens\\" + usuario.getNomeCompleto();

        File fileToSave = new File(fotoUrl);
        if (!fileToSave.exists()) {
            fileToSave.mkdir();
        }

        fileToSave = new File(fotoUrl + "\\"
                + imageFile.getOriginalFilename());

        OutputStream os = new FileOutputStream(fileToSave);
        os.write(imageFile.getBytes());
        os.close();

        Postagem post = Postagem.builder()
                .numeroCurtidas(0)
                .numeroCompartilhamentos(0)
                .dataHoraPostagem(LocalDateTime.now())
                .descricao(descPostagem)
                .urlImagemPost("http://localhost:8080/usuario/" + imageFile.getOriginalFilename())
                .build();

        usuario.getPostagens().add(post);

        return postagemRepository.save(post);
    }

    @Override
    public Postagem delete(Postagem postagem) {
        Postagem postagemRemove = findById(postagem.getId());
        postagemRepository.delete(postagemRemove);
        return postagemRemove;
    }

    @Override
    public Postagem update(Postagem postagem) {
        Postagem postagemUpdate = findById(postagem.getId());
        if (postagemUpdate != null) {
            postagemUpdate = postagem;
            postagemRepository.save(postagemUpdate);
        }
        return postagemUpdate;
    }

    @Override
    public Postagem findById(UUID id) {
        Optional<Postagem> postagemOptional = postagemRepository.findById(id);
        return postagemOptional.orElseThrow(() -> new RuntimeException("Postagem não encontrada"));
    }

    @Override
    public List<Postagem> listar() {
        return postagemRepository.findAll();
    }
}
