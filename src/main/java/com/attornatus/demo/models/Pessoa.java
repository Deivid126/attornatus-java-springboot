package com.attornatus.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotBlank
    @Column(nullable = false, name="nome")
    private String nome;

    @NotNull
    @Column(nullable = false, name="data_nascimento")
    @Temporal(TemporalType.DATE)
    private LocalDate datanascimento;

    @NotNull
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Endereco> enderecos = new ArrayList<>();
}
