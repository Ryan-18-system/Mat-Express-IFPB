package br.edu.ifpb.matexpress.model.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @NotBlank(message = "Campo obrigatório!")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
    private String nome;

    @NotBlank(message = "Campo obrigatório!")
    @Size(max = 20, message = "A sigla deve ter no máximo 20 caracteres")
    @Pattern(regexp = "[A-Z]{1,8}", message = "A sigla deve ser composta apenas por letras maiúsculas")
    private String sigla;

    @NotBlank(message = "Campo obrigatório!")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    @Pattern(regexp = "\\([1-9]{2}\\)\\s9?[6-9][0-9]{3}\\-[0-9]{4}", message = "O telefone deve estar no formato (99) 99999-9999")
    private String telefone;

    @OneToMany
    @JoinColumn(name = "instituicao_id")
    private List<PeriodoLetivo> periodos = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "periodo_atual_id")
    private PeriodoLetivo periodoAtual;

    public void addPeriodo(PeriodoLetivo newPeriodo) {
        this.periodos.add(newPeriodo);
    }

    @PrePersist
    private void prePersist() {
        this.sigla = this.sigla.toUpperCase();
    }
}
