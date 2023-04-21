package br.edu.ifpb.matexpress.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Table(name = "tb_declaracao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Declaracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataRecebimento;

    private  String observacoes;

    private LocalDate dataVencimento;

    @OneToOne
    @JoinColumn(name = "periodo_id")
    private PeriodoLetivo periodoLetivo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Estudante titular;

    private  Boolean declaracaoAtual = true;

}