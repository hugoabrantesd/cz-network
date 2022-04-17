package br.edu.fafic.cz_network.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private LocalDate dataAniversario;

    private String descricaoBio;

    private String urlFoto;

    @Column(unique = true, nullable = false)
    private String email;

    private String numeroWhatsapp;

    @OneToMany(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Usuario> seguindo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Postagem> postagens;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Pagina> paginasCurtidas;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Pagina> paginasCriadas;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InteressesPessoais> interessesPessoais;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    private List<Endereco> enderecos;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Educacao> educacao;

    private String visibilidadeDoPerfil;

    @ElementCollection
    private List<String> notificacoes;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate contaCriadaEm;
}

