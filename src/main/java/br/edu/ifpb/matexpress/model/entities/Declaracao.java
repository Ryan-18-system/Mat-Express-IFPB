package br.edu.ifpb.matexpress.model.entities;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_declaracao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Declaracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Campo obrigatório!")
    private LocalDate dataRecebimento;

    @Length(max = 150, message = "O campo deve ter no máximo 150 caracteres")
    private String observacoes;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Campo obrigatório!")
    private LocalDate dataVencimento;

    @OneToOne
    @JoinColumn(name = "periodo_id")
    private PeriodoLetivo periodoLetivo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    private Estudante titular;

    private Boolean declaracaoAtual = true;

}
