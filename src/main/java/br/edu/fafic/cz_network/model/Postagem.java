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
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String descricao;
    private Integer numeroCurtidas;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comentario> comentarios;
    private Integer numeroCompartilhamentos;
    private String usuario; //TODO: MUDAR PARA O TIPO "Usuario"
    private LocalDateTime dataHoraPostagem;


}
