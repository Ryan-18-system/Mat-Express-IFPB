package br.edu.ifpb.matexpress.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String nome;
    private String matricula;

    @OneToOne
    @JoinColumn(name = "instituicao_atual_id")
    private Instituicao instituicaoAtual;

    @OneToMany(mappedBy = "titular", cascade = CascadeType.ALL)
    private List<Declaracao> declaracoes = new ArrayList<>();

}
