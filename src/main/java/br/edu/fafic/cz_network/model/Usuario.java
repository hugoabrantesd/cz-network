package br.edu.fafic.cz_network.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "id")
    private UUID uuid;

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

    @ElementCollection
    //@OneToMany
    private List<String> postagens; // TODO: mudar para o tipo 'Postagem'

    @ElementCollection
    //@OneToMany
    private List<String> paginasCurtidas; // TODO: mudar para o tipo 'Pagina'

    @ElementCollection
    //@OneToMany
    private List<String> paginasCriadas; // TODO: mudar para o tipo 'Pagina'

    @ElementCollection
    //@OneToMany
    private List<String> interessesPessoais; // TODO: mudar para o tipo 'Interesse'

    @Embedded
    @Column(nullable = false)
    private Endereco endereco;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Educacao> locaisOndeEstudou;

    private String visibilidadeDoPerfil;

    @ElementCollection
    //@OneToMany
    private List<String> notificacoes;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate contaCriadaEm;
}









