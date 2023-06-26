package br.edu.ifpb.matexpress.model.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

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

    @NotNull(message = "Campo obrigatório!")
    private Integer ano;

    @NotNull(message = "Campo obrigatório!")
    private Integer periodo;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inicio;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fim;

    @Transient
    private Instituicao instituicaoPertencente;

}
