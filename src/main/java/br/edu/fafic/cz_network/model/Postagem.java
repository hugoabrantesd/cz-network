package br.edu.fafic.cz_network.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    private LocalDateTime dataHoraPostagem;
}
