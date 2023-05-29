package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.entities.Declaracao;
import br.edu.ifpb.matexpress.model.entities.Estudante;
import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.services.DeclaracaoService;
import br.edu.ifpb.matexpress.model.services.EstudanteService;
import br.edu.ifpb.matexpress.model.services.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView cadastrarDeclaracao(Declaracao declaracao, ModelAndView modelAndView){
        modelAndView.setViewName("redirect:/matexpress/estudantes");
        declaracaoService.novaDeclaracao(declaracao);
        return modelAndView;
    }
    @GetMapping("gerar-pdf/{id}")
    public ResponseEntity<byte[]> gerarPdf(@PathVariable("id") Long id){
        return  this.declaracaoService.gerarPdfPorId(id);
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

    @PostMapping("/inserir-dias")
    public ModelAndView declaracoesAVencer(ModelAndView modelAndView) {
        modelAndView.setViewName("declaracoes/inserir-dias");
        return modelAndView;
    }

    @GetMapping("/relatorio/{quantidadeDias}")
    public ModelAndView relatoriosDeclaracoes(@PathVariable Integer quantidadeDias, ModelAndView modelAndView) {
        List<Declaracao> declaracoesAVencer = declaracaoService.obterDeclaracoesAVencer(quantidadeDias);
        modelAndView.addObject("declaracoesAVencer", declaracoesAVencer);
        return modelAndView;
    }
        private List<PeriodoLetivo> listarPeriodosDaIntituicao(Long id){
        return this.instituicaoService.listarPeriodosDaInstituicao(id);
    }

    private Estudante getEstudante(Long id){
        return this.estudanteService.pesquisarPorId(id);
    }

}
