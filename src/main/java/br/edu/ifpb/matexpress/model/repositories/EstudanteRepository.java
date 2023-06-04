package br.edu.ifpb.matexpress.model.repositories;

import br.edu.ifpb.matexpress.model.entities.Declaracao;
import br.edu.ifpb.matexpress.model.entities.Estudante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante,Long> {
    Optional<Estudante> getEstudanteByMatricula(String matricula);

    @Query("SELECT e FROM Estudante e WHERE e.instituicaoAtual.id = :instituicaoId")
    List<Estudante> estudantesDeUmaIntituicao(@Param("instituicaoId") Long instituicaoId);

    @Query("SELECT e FROM Estudante e WHERE e.declaracoes.size = 0")
    List<Estudante> estudanteSemDeclaracao(@Param("declaracao") Declaracao declaracao);

    Page<Estudante> findAll(Pageable pageable);

}