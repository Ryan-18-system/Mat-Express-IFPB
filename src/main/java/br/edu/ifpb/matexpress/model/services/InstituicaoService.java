package br.edu.ifpb.matexpress.model.services;

import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.repositories.InstituicaoRepository;
import br.edu.ifpb.matexpress.model.repositories.PeriodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoService {
    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private PeriodoRepository periodoRepository;

    @Transactional
    public String cadastrarInstituicao(Instituicao newInstituicao) {
        Optional<Instituicao> instituicaoBanco = this.instituicaoRepository.getInstituicaoBySiglaEquals(newInstituicao.getSigla().toUpperCase());
        if (instituicaoBanco.isPresent()) {
            Instituicao editInstituicao = instituicaoBanco.get();
            editInstituicao.setNome(newInstituicao.getNome());
            editInstituicao.setTelefone(newInstituicao.getTelefone());
            editInstituicao.setSigla(newInstituicao.getSigla());
            editInstituicao.setPeriodoAtual(newInstituicao.getPeriodoAtual());
            if(!editInstituicao.getPeriodos().contains(newInstituicao.getPeriodoAtual())){
                editInstituicao.addPeriodo(newInstituicao.getPeriodoAtual());
            }

            this.instituicaoRepository.save(editInstituicao);
            return "Instituição Editada com sucesso";
        }
        newInstituicao.addPeriodo(newInstituicao.getPeriodoAtual());
        this.instituicaoRepository.save(newInstituicao);
        return "Instituição cadastrada com sucesso";
    }

    public List<Instituicao> listarInstituicoes() {
        return this.instituicaoRepository.findAll();
    }

    public List<PeriodoLetivo> listarPeriodosCadastrados() {
        return this.periodoRepository.listarPeriodosSemInstituicoes();
    }
    public List<PeriodoLetivo>listarPeriodosDaInstituicao(Long idInstituicao){
        return this.periodoRepository.listarPeriodosPorIdIntituicao(idInstituicao);
    };

    public Instituicao pesquisarPorId(Long idInstituicao) {
        Optional<Instituicao> instituicaoBanco = this.instituicaoRepository.findById(idInstituicao);
        return instituicaoBanco.orElseGet(instituicaoBanco::orElseThrow);
    }

    public String deletarPorId(Long idInstituicao) {
        Optional<Instituicao> instituicaoBanco = this.instituicaoRepository.findById(idInstituicao);
        if (instituicaoBanco.isPresent()) {
            this.instituicaoRepository.deleteById(idInstituicao);
            return "Instituição deletado com sucesso";
        }
        return "Instituição não encontrado, impossível deletar";
    }
}
