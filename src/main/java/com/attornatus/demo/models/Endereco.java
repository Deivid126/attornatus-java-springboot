package com.attornatus.demo.models;

import com.attornatus.demo.enums.EnderecoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    @Column(nullable = false, name = "logradouro")
    private String logradouro;

    @NotBlank
    @Column(nullable = false, name = "caixa_postal")
    private String caixaPostal;

    @Positive
    @Column(nullable = false, name = "numero")
    private int numero;

    @NotBlank
    @Column(nullable = false, name = "cidade")
    private String cidade;

    @NotNull
    private EnderecoEnum tipoEndereco;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonIgnoreProperties("enderecos")
    private Pessoa pessoa;
}
