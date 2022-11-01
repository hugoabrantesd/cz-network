package br.edu.fafic.cz_network.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagina {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String sobre;
    private String foto;
    @OneToMany
    private List<Postagem> postagens;
    //private List<Usuario> curtidores; TODO: MUDAR PARA O TIPO "Usuario"
    private String usuario;
    private String visibilidade;
    private LocalDateTime dataHoraCriacao;
    @Embedded
    private Categoria categoria;


}
