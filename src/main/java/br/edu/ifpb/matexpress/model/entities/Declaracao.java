package br.edu.ifpb.matexpress.model.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_id")
    @ToString.Exclude
    private Documento documento;

    private LocalDate dataVencimento;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "periodo_id", nullable = true)
    private PeriodoLetivo periodoLetivo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Estudante titular;

    private Boolean declaracaoAtual = true;

}
