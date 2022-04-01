package br.edu.fafic.cz_network.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {

    private String uf;

    private String cidade;

    private String pais;

    private String cep;

    private String rua;

    private Integer numero;

    private String bairro;

    private String complemento;


}
