package br.edu.ifpb.matexpress.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String matricula;

    @OneToMany
    @JoinColumn(name = "estudante_id")
    private List<Instituicao> instituicoes = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "instituicao_atual_id")
    private Instituicao instituicaoAtual;

    public void addInstituicao(Instituicao newInstituicao){
        this.instituicoes.add(newInstituicao);
    }
}
