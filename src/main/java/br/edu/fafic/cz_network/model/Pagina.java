package br.edu.fafic.cz_network.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private UUID id;
    private String nome;
    private String sobre;
    private String foto;
    @OneToMany
    private List<Postagem> postagens;
   // private List<String> curtidores; //TODO: MUDAR PARA O TIPO "Usuario"
    private String usuario; //TODO: MUDAR PARA O TIPO "Usuario"
    private String visibilidade;
    private LocalDateTime dataHoraCriacao;
    @OneToOne
    private Categoria categoria;


}
