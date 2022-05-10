package br.edu.fafic.cz_network.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
    private String categoria;

}
