package br.edu.ifpb.matexpress.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tb_periodo_letivo")
public class PeriodoLetivo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="Campo obrigatório!")
    private Integer ano;

    @NotNull(message="Campo obrigatório!")
    private Integer periodo;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message="Campo obrigatório!")
    private LocalDate inicio;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message="Campo obrigatório!")
    private LocalDate fim;

}
