package br.edu.ifpb.matexpress.model.services;

import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.repositories.InstituicaoRepository;
import br.edu.ifpb.matexpress.model.repositories.PeriodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InstituicaoService {
    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private PeriodoRepository periodoRepository;

    @Transactional
    public String cadastrarInstituicao(Instituicao newInstituicao) {
        if(Objects.isNull(newInstituicao.getId())){
            instituicaoRepository.save(newInstituicao);
            return "Instituição cadastrada com sucesso";
        }
        Optional<Instituicao> instituicaoBanco = this.instituicaoRepository.findById(newInstituicao.getId());
       if(instituicaoBanco.isPresent()){
           instituicaoBanco.get().setNome(newInstituicao.getNome());
           instituicaoBanco.get().setSigla(newInstituicao.getSigla());
           instituicaoBanco.get().setTelefone(newInstituicao.getTelefone());
           instituicaoBanco.get().setPeriodoAtual(newInstituicao.getPeriodoAtual());
           instituicaoRepository.save(instituicaoBanco.get());
           return "Instituição editada com sucesso";
       }
       return "";
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
            return "Instituição deletada com sucesso";
        }
        return "Instituição não encontrada, impossível deletar";
    }
}
