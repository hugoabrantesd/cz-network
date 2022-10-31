package br.edu.fafic.cz_network.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ToString
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private UUID id;

    private String nomeCompleto;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataAniversario;

    private String descricaoBio;

    private String urlFoto;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    private String numeroWhatsapp;

    @OneToMany(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private List<Usuario> seguindo;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Postagem> postagens;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Pagina> paginasCurtidas;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Pagina> paginasCriadas;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<InteressesPessoais> interessesPessoais;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(nullable = false)
    @ToString.Exclude
    private List<Endereco> enderecos;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Educacao> educacao;

    private String visibilidadeDoPerfil;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Notificacao> notificacoes;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate contaCriadaEm;
}

