package br.edu.ifpb.matexpress.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_estudante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Campo obrigatório!")
    private String nome;

    @Digits(integer = 11, fraction = 0, message = "Informe um número de até 11 dígitos!")
    @NotBlank(message="Campo obrigatório!")
    private String matricula;


    @OneToOne()
    @JoinColumn(name = "instituicao_atual_id",nullable = true)
    private Instituicao instituicaoAtual;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Declaracao> declaracoes = new ArrayList<>();

}
