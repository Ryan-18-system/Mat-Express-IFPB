package br.edu.ifpb.matexpress.model.services;

import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.repositories.InstituicaoRepository;
import br.edu.ifpb.matexpress.model.repositories.PeriodoRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Service
public class PeriodoService {
    @Autowired
    private PeriodoRepository periodoRepository;
    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Transactional
    public String cadastrarPeriodo(PeriodoLetivo newPeriodo) {
        Optional<Instituicao> instituicaoBanco = instituicaoRepository.findById(newPeriodo.getInstituicaoPertencente().getId());
        if (instituicaoBanco.isPresent()) {
            Instituicao instituicao = instituicaoBanco.get();
            if (newPeriodo.getInicio().isAfter(newPeriodo.getFim()) || newPeriodo.getInicio().isEqual(newPeriodo.getFim())){
                return "Data do período inválida";
            }
            instituicao.setPeriodoAtual(newPeriodo);
            instituicao.getPeriodos().add(newPeriodo);
            this.periodoRepository.save(newPeriodo);
            return "Período cadastrado com sucesso";
        }

        if (newPeriodo.getId() != null) {
            PeriodoLetivo periodoExistente = periodoRepository.findById(newPeriodo.getId()).orElse(null);
            if (periodoExistente != null) {
                periodoExistente.setFim(newPeriodo.getFim());
                periodoExistente.setInicio(newPeriodo.getInicio());
                periodoExistente.setPeriodo(newPeriodo.getPeriodo());
                if (periodoExistente.getInicio().isAfter(periodoExistente.getFim()) || periodoExistente.getInicio().isEqual(periodoExistente.getFim())){
                    return "Data do período inválida";
                }
                periodoRepository.save(periodoExistente);
                return "Período cadastrado com sucesso";
            }
        }
        return "";
    }


    public List<PeriodoLetivo> listarPeriodos() {
        return this.periodoRepository.findAll();
    }

    public PeriodoLetivo pesquisarPeriodoPorId(Long idPeriodo) {
        Optional<PeriodoLetivo> periodoBanco = periodoRepository.findById(idPeriodo);
        return periodoBanco.orElseGet(periodoBanco::orElseThrow);
    }
    public void deletarPorId(Long idPeriodo) throws Exception{
            PeriodoLetivo periodoBanco = this.pesquisarPeriodoPorId(idPeriodo);
            this.periodoRepository.delete(periodoBanco);

    }

}
