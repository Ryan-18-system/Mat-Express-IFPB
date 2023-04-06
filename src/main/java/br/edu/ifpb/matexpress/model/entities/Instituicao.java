package br.edu.ifpb.matexpress.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_instituicao")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Instituicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sigla;
    private String telefone;

    @OneToMany
    @JoinColumn(name = "instituicao_id")
    private List<PeriodoLetivo> periodos;

    @OneToOne
    @JoinColumn(name = "periodo_atual_id")
    private PeriodoLetivo periodoAtual;
}
