package br.edu.ifpb.matexpress.controllers;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.matexpress.model.repositories.InstituicaoRepository;
import br.edu.ifpb.matexpress.util.NavPage;
import br.edu.ifpb.matexpress.util.NavPageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.services.InstituicaoService;
import javax.validation.Valid;

@Controller
@RequestMapping("/instituicoes")
public class InstituicaoController {
    @Autowired
    private InstituicaoService instituicaoService;

    @Autowired
    private InstituicaoRepository instituicaoRepository;
    String mensagem;

    @GetMapping("/")
    public ModelAndView formInstituicoes(Instituicao instituicao, ModelAndView modelAndView) {
        modelAndView.setViewName("instituicoes/form");
        modelAndView.addObject("instituicao", instituicao);
        return modelAndView;
    }

    @PostMapping("**/cadastrar")
    public ModelAndView cadastrarInstituicao(@Valid Instituicao instituicao, BindingResult validation,
            ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (validation.hasErrors()) {
            modelAndView.setViewName("instituicoes/form");
            return modelAndView;
        }
        mensagem = this.instituicaoService.cadastrarInstituicao(instituicao);
        modelAndView.setViewName("redirect:/instituicoes");
        redirectAttributes.addFlashAttribute("mensagem", mensagem);
        return modelAndView;
    }

    @GetMapping("")
    public ModelAndView listarInstituicoes(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "5") int size, ModelAndView modelAndView) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Instituicao> instituicoesPage = this.instituicaoRepository.findAll(paging);
        NavPage navPage = NavPageBuilder.newNavPage(instituicoesPage.getNumber() + 1,
                instituicoesPage.getTotalElements(), instituicoesPage.getTotalPages(), size);
        modelAndView.addObject("instituicoes", instituicoesPage);
        modelAndView.addObject("navPage", navPage);
        modelAndView.setViewName("instituicoes/listagem");

        return modelAndView;
    }

    @GetMapping("/editarinstituicao/{idInstituicao}")
    public ModelAndView editarInstituicao(ModelAndView modelAndView,
            @PathVariable("idInstituicao") Long idInstituicao) {
        // periodos.addAll(this.instituicaoService.listarPeriodosCadastrados());
        List<PeriodoLetivo> periodos = new ArrayList<>(
                this.instituicaoService.listarPeriodosDaInstituicao(idInstituicao));
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
        modelAndView.setViewName("redirect:/instituicoes");
        return modelAndView;
    }

    @ModelAttribute("periodos")
    public List<PeriodoLetivo> periodosLetivos() {
        return this.instituicaoService.listarPeriodosCadastrados();
    }
}
