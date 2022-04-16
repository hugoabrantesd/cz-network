package br.edu.fafic.cz_network.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InteressesPessoais {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String interesses;
}
