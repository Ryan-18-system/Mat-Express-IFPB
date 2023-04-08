package br.edu.ifpb.matexpress.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tb_instituicao")
@Data
@EqualsAndHashCode(exclude = "periodos")
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
    private List<PeriodoLetivo> periodos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "periodo_atual_id")
    private PeriodoLetivo periodoAtual;

    public void addPeriodo(PeriodoLetivo newPeriodo){
        this.periodos.add(newPeriodo);
    }
    @PrePersist
    private void prePersist() {
        this.sigla = this.sigla.toUpperCase();
    }
}
