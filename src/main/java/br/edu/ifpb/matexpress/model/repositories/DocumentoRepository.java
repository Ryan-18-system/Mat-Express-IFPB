package br.edu.ifpb.matexpress.model.repositories;

import br.edu.ifpb.matexpress.model.entities.Documento;
import br.edu.ifpb.matexpress.model.entities.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}
