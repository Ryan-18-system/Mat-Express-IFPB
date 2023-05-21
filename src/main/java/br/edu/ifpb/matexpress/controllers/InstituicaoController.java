package br.edu.ifpb.matexpress.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.services.InstituicaoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/instituicoes")
public class InstituicaoController {
    @Autowired
    private InstituicaoService instituicaoService;
    String mensagem;

    @GetMapping("/")
    public ModelAndView formInstituicoes(Instituicao instituicao, ModelAndView modelAndView) {
        modelAndView.setViewName("instituicoes/form");
        modelAndView.addObject("instituicao", instituicao);
        return modelAndView;
    }

    @PostMapping("**/cadastrar")
    public ModelAndView cadastrarInstituicao(@Valid Instituicao instituicao,BindingResult validation,
            ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (validation.hasErrors()) {
            modelAndView.setViewName("instituicoes/form");
            return modelAndView;
        }
        mensagem = this.instituicaoService.cadastrarInstituicao(instituicao);
        modelAndView.setViewName("redirect:/matexpress/instituicoes");
        redirectAttributes.addFlashAttribute("mensagem", mensagem);
        return modelAndView;
    }

    @GetMapping("")
    public ModelAndView listarInstituicoes(ModelAndView modelAndView) {
        modelAndView.setViewName("instituicoes/listagem");
        modelAndView.addObject("instituicoes", this.instituicaoService.listarInstituicoes());
        return modelAndView;
    }

    @GetMapping("/editarinstituicao/{idInstituicao}")
    public ModelAndView editarInstituicao(ModelAndView modelAndView,
            @PathVariable("idInstituicao") Long idInstituicao) {
        List<PeriodoLetivo> periodos = new ArrayList<>();
        periodos.addAll(this.instituicaoService.listarPeriodosCadastrados());
        periodos.addAll(this.instituicaoService.listarPeriodosDaInstituicao(idInstituicao));
        modelAndView.setViewName("instituicoes/form");
        modelAndView.addObject("instituicao", instituicaoService.pesquisarPorId(idInstituicao));
        modelAndView.addObject("periodosDaInstituicao", periodos);
        return modelAndView;
    }

    @GetMapping("/removerinstituicao/{idInstituicao}")
    public ModelAndView removerInstituicao(ModelAndView modelAndView,
            @PathVariable("idInstituicao") Long idInstituicao,
            RedirectAttributes redirectAttributes) {
        mensagem = this.instituicaoService.deletarPorId(idInstituicao);
        redirectAttributes.addFlashAttribute("mensagem", mensagem);
        modelAndView.setViewName("redirect:/matexpress/instituicoes");
        return modelAndView;
    }

    @ModelAttribute("periodos")
    public List<PeriodoLetivo> periodosLetivos() {
        return this.instituicaoService.listarPeriodosCadastrados();
    }
}
