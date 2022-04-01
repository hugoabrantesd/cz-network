package br.edu.fafic.cz_network.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, name = "id")
    private UUID uuid;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate birthDate;

    private String bio;
    private String photo; // foto do usuário convertida em string
    private String email;
    private String whatsapp;

    @OneToMany
    private List<UserModel> seguindo;

    @ElementCollection
    // @OneToMany // TODO: mudar quando mudar o tipo abaixo
    private List<String> postagens; // TODO: mudar para o tipo 'Postagem'

    @ElementCollection
//    @OneToMany
    private List<String> paginasCurtidas;

    @ElementCollection
//    @OneToMany
    private List<String> paginasCriadas;

    @ElementCollection
//    @OneToMany
    private List<String> interessesPessoais;

    @Embedded
    private Endereco endereco;

    @Embedded
    private Educacao locaisOndeEstudou;

    private String visibilidadeDoPerfil;

    @ElementCollection
//    @OneToMany
    private List<String> notificacoes;

    private String visibilidade;

    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate contaCriadaEm;

}









