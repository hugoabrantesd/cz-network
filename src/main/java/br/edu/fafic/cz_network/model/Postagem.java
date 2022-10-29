package br.edu.fafic.cz_network.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
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
    @ToString.Exclude
    private List<Comentario> comentarios;

    private Integer numeroCompartilhamentos;

    @OneToOne
    @ToString.Exclude
    @JsonIgnore
    private Usuario usuario;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dataHoraPostagem;
}
