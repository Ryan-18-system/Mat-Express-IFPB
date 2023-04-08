package br.edu.ifpb.matexpress.model.repositories;
import br.edu.ifpb.matexpress.model.entities.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface InstituicaoRepository extends JpaRepository<Instituicao,Long> {

    Optional<Instituicao> getInstituicaoBySiglaEquals(String sigla);

}
