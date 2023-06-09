package br.edu.ifpb.matexpress.model.repositories;

import br.edu.ifpb.matexpress.model.entities.Declaracao;

import br.edu.ifpb.matexpress.model.entities.Documento;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeclaracaoRepository extends JpaRepository<Declaracao, Long> {

    @Modifying
    @Query("UPDATE Declaracao d SET d.declaracaoAtual = false")
    void atualizarTodasDeclaracoesAtualParaFalse();

    List<Declaracao> getDeclaracaoByTitular(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE Declaracao d SET d.declaracaoAtual = false WHERE d.id = :declaracaoId")
    void atualizarAtualParaFalsePorId(Long declaracaoId);

    @Query("SELECT d FROM Declaracao d WHERE d.dataVencimento = :dataVencimento")
    List<Declaracao> findByDataVencimento(@Param("dataVencimento") LocalDate dataVencimento);

    @Query("SELECT d FROM Declaracao d WHERE d.dataVencimento < :dataVencimento")
    List<Declaracao> findByDataVencimentoMenorQue(@Param("dataVencimento") LocalDate dataVencimento);
}
