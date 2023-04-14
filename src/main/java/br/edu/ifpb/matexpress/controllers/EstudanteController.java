package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.entities.Estudante;
import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.services.EstudanteService;
import br.edu.ifpb.matexpress.model.services.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/estudantes")
public class EstudanteController {
    @Autowired
    private EstudanteService estudanteService;

    @Autowired
    private InstituicaoService instituicaoService;
    String mensagem;


    @GetMapping("/")
    public ModelAndView formEstudantes(Estudante estudante, ModelAndView modelAndView) {
        modelAndView.setViewName("estudantes/form");
        modelAndView.addObject("estudante", estudante);
        return modelAndView;
    }

    @PostMapping("**/cadastrar")
    public ModelAndView cadastrarEstudante(Estudante estudante, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        mensagem = this.estudanteService.cadastrarEstudante(estudante);
        modelAndView.setViewName("redirect:/matexpress/estudantes");
        redirectAttributes.addFlashAttribute("mensagem", mensagem);
        return modelAndView;
    }

    @GetMapping("")
    public ModelAndView listarEstudantes(ModelAndView modelAndView) {
        modelAndView.setViewName("estudantes/listagem");
        modelAndView.addObject("estudantes", this.estudanteService.listarEstudantes());
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
}
