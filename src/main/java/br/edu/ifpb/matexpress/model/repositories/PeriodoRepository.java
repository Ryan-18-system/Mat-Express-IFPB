package br.edu.ifpb.matexpress.model.repositories;

import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodoRepository extends JpaRepository<PeriodoLetivo,Long> {
}
