package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.entities.Declaracao;
import br.edu.ifpb.matexpress.model.entities.Estudante;
import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.repositories.DeclaracaoRepository;
import br.edu.ifpb.matexpress.model.services.DeclaracaoService;
import br.edu.ifpb.matexpress.model.services.EstudanteService;
import br.edu.ifpb.matexpress.model.services.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/declaracoes")
public class DeclaracaoController {

    @Autowired
    private DeclaracaoService declaracaoService;
    @Autowired
    private InstituicaoService instituicaoService;

    @Autowired
    private EstudanteService estudanteService;

    @GetMapping()
    public ModelAndView homeDeclaracoes(ModelAndView modelAndView){
        modelAndView.setViewName("declaracoes/listagem");
        return  modelAndView;
    }


    @PostMapping("salvar")
    public  ModelAndView cadastrarDeclaracao(ModelAndView modelAndView, Declaracao declaracao,RedirectAttributes redirectAttributes){
        modelAndView.setViewName("redirect:/matexpress/declaracoes");
        declaracaoService.novaDeclaracao(declaracao);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView formDeclaracoes(ModelAndView modelAndView, Declaracao declaracao, @PathVariable("id") Long id){
        Estudante estudantePesquisado = this.getEstudante(id);
        Instituicao instituicao = estudantePesquisado.getInstituicaoAtual();
        modelAndView.setViewName("declaracoes/form");
        modelAndView.addObject("declaracao", declaracao);
        modelAndView.addObject("estudantePesquisado",estudantePesquisado);
        modelAndView.addObject("instituicao",instituicao);
        modelAndView.addObject("periodos", this.listarPeriodosDaIntituicao(instituicao.getId()));
        return  modelAndView;
    }


    private List<PeriodoLetivo> listarPeriodosDaIntituicao(Long id){
        return this.instituicaoService.listarPeriodosDaInstituicao(id);
    }

    private Estudante getEstudante(Long id){
        return this.estudanteService.pesquisarPorId(id);
    }

}
