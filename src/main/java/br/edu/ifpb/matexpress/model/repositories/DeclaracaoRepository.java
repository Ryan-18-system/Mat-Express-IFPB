package br.edu.ifpb.matexpress.model.repositories;

import br.edu.ifpb.matexpress.model.entities.Declaracao;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclaracaoRepository extends JpaRepository<Declaracao, Long> {

    @Modifying
    @Query("UPDATE Declaracao d SET d.declaracaoAtual = false")
    void atualizarTodasDeclaracoesAtualParaFalse();

    @Transactional
    @Modifying
    @Query("UPDATE Declaracao d SET d.declaracaoAtual = true WHERE d.id = :declaracaoId")
    void atualizarAtualParaFalsePorId(Long declaracaoId);
}
