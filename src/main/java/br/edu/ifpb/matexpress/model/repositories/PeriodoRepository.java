package br.edu.ifpb.matexpress.model.repositories;

import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PeriodoRepository extends JpaRepository<PeriodoLetivo,Long> {
    @Query(value = "select * from tb_periodo_letivo where instituicao_id is null ",nativeQuery = true)
    List<PeriodoLetivo> listarPeriodosSemInstituicoes();
    @Query(value="select * from tb_periodo_letivo where  instituicao_id = :id ",nativeQuery = true)
    List<PeriodoLetivo> listarPeriodosPorIdIntituicao(Long id);
}
