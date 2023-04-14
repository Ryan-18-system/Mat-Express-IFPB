package br.edu.ifpb.matexpress.model.services;

import br.edu.ifpb.matexpress.model.entities.Estudante;
import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.repositories.EstudanteRepository;
import br.edu.ifpb.matexpress.model.repositories.InstituicaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudanteService {

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Transactional
    public String cadastrarEstudante(Estudante newEstudante) {
        Optional<Estudante> estudanteOptional = this.estudanteRepository.getEstudanteByMatricula(newEstudante.getMatricula());
        if (estudanteOptional.isPresent()) {
            Estudante editEstudante = estudanteOptional.get();
            editEstudante.setNome(newEstudante.getNome());
            editEstudante.setMatricula(newEstudante.getMatricula());
            editEstudante.setInstituicaoAtual(newEstudante.getInstituicaoAtual());

            this.estudanteRepository.save(editEstudante);
            return "Estudante editado com sucesso";
        }
        this.estudanteRepository.save(newEstudante);
        return "Estudante cadastrado com sucesso";
    }

    public List<Estudante> listarEstudantes() {
        return this.estudanteRepository.findAll();
    }

    public List<Instituicao> listarInstituicoesCadastradas() {
        return this.instituicaoRepository.findAll();
    }



    public Estudante pesquisarPorId(Long idEstudante) {
        Optional<Estudante> estudanteOptional = this.estudanteRepository.findById(idEstudante);
        return estudanteOptional.orElseGet(estudanteOptional::orElseThrow);
    }

    public String deletarPorId(Long idEstudante) {
        Optional<Estudante> estudanteOptional = this.estudanteRepository.findById(idEstudante);
        if (estudanteOptional.isPresent()) {
            this.estudanteRepository.deleteById(idEstudante);
            return "Estudante deletado com sucesso";
        }
        return "Estudante não encontrado, impossível deletar";
    }
}
