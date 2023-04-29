package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.entities.Declaracao;
import br.edu.ifpb.matexpress.model.entities.Estudante;
import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.services.EstudanteService;
import br.edu.ifpb.matexpress.model.services.InstituicaoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/estudantes")
public class EstudanteController {
    @Autowired
    private EstudanteService estudanteService;

    @Autowired
    private InstituicaoService instituicaoService;
    String mensagem;

    @GetMapping("/declaracoes/{id}")
    public ModelAndView declaracoes(ModelAndView modelAndView,@PathVariable("id") Long id){
        modelAndView.setViewName("estudantes/listagem-declaracoes");
        modelAndView.addObject("declaracoes", this.declaracoes(id));
        return modelAndView;
    }
    @GetMapping("/")
    public ModelAndView formEstudantes(Estudante estudante, ModelAndView modelAndView) {
        modelAndView.setViewName("estudantes/form");
        modelAndView.addObject("estudante", estudante);
        return modelAndView;
    }

    @PostMapping("**/cadastrar")
    public ModelAndView cadastrarEstudante(@Valid Estudante estudante, BindingResult validation,
        ModelAndView modelAndView, RedirectAttributes redirectAttributes) {   
        if(validation.hasErrors()){
            modelAndView.setViewName("estudantes/form");
            return modelAndView;
        }
        mensagem = this.estudanteService.cadastrarEstudante(estudante);  
        modelAndView.setViewName("redirect:/matexpress/estudantes");
        redirectAttributes.addFlashAttribute("mensagem", mensagem);
        return modelAndView;
    }

    @GetMapping("")
    public ModelAndView listarEstudantes(ModelAndView modelAndView) {
        modelAndView.setViewName("estudantes/listagem");
        return modelAndView;
    }

    @GetMapping("/editarestudante/{idEstudante}")
    public ModelAndView editarEstudante(ModelAndView modelAndView,
                                          @PathVariable("idEstudante") Long idEstudante
    ) {
        List<Instituicao> instituicoes = new ArrayList<>();
        instituicoes.addAll(this.estudanteService.listarInstituicoesCadastradas());
        instituicoes.addAll(this.instituicaoService.listarInstituicoes());
        modelAndView.setViewName("estudantes/form");
        modelAndView.addObject("estudante", estudanteService.pesquisarPorId(idEstudante));
        modelAndView.addObject("instituicoes", instituicoes);
        return modelAndView;
    }

    @GetMapping("/removerestudante/{idEstudante}")
    public ModelAndView removerEstudante(ModelAndView modelAndView,
                                           @PathVariable("idEstudante") Long idEstudante,
                                           RedirectAttributes redirectAttributes) {
        mensagem = this.estudanteService.deletarPorId(idEstudante);
        redirectAttributes.addFlashAttribute("mensagem", mensagem);
        modelAndView.setViewName("redirect:/matexpress/estudantes");
        return modelAndView;
    }

    @ModelAttribute("instituicoes")
    public List<Instituicao> instituicoes() {
        return this.estudanteService.listarInstituicoesCadastradas();
    }

    @ModelAttribute("estudantes")
    public List<Estudante> esttudantes() {
        return this.estudanteService.listarEstudantes();
    }


    public List<Declaracao> declaracoes(Long idEstudante) {
        Estudante ePesquisado = this.estudanteService.pesquisarPorId(idEstudante);
        return  ePesquisado.getDeclaracoes();
    }
}
