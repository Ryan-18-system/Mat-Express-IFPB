package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.services.InstituicaoService;
import br.edu.ifpb.matexpress.model.services.PeriodoService;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/periodoletivo")
public class PeriodoController {
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private InstituicaoService instituicaoService;

    private String mensagem;

    @GetMapping("/form")
    public String formPeriodo(PeriodoLetivo periodoLetivo, Model model) {
        model.addAttribute("periodoLetivo", periodoLetivo);
        return "periodoletivo/form";
    }

    @PostMapping("**/salvarperiodo")
    public ModelAndView cadastrarPeriodoLetivo(@Valid PeriodoLetivo periodoLetivo, BindingResult result, ModelAndView mv, RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            mv.setViewName("periodoletivo/form");
            return mv;
        }
        mensagem = this.periodoService.cadastrarPeriodo(periodoLetivo);
        if (mensagem.equals("Data do período inválida")){
            redirectAttributes.addFlashAttribute("mensagemErro", mensagem);
            return new ModelAndView("redirect:/matexpress/periodoletivo/form");

        }
        mv.setViewName("redirect:/matexpress/periodoletivo/listarperiodos");
        redirectAttributes.addFlashAttribute("mensagem", mensagem);
        return mv;
    }

    @GetMapping("**/listarperiodos")
    public ModelAndView listarPeriodos(ModelAndView modelAndView) {
        modelAndView.setViewName("periodoletivo/listagem");
        return modelAndView;
    }

    @ModelAttribute("periodos")
    public List<PeriodoLetivo> periodoLetivos() {
        return periodoService.listarPeriodos();
    }

    @ModelAttribute("instituicoes")
    public List<Instituicao> instituicoes() {
        return instituicaoService.listarInstituicoes();
    }

    @GetMapping("/editarperiodo/{idPeriodo}")
    public ModelAndView editarPeriodo(ModelAndView modelAndView, @PathVariable("idPeriodo") Long idPeriodo) {
        modelAndView.setViewName("periodoletivo/form");
        modelAndView.addObject("periodoLetivo", periodoService.pesquisarPeriodoPorId(idPeriodo));
        return modelAndView;
    }

    @GetMapping("/removerperiodo/{idPeriodo}")
    public String removerPeriodo(@PathVariable("idPeriodo") Long idPeriodo, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            periodoService.deletarPorId(idPeriodo);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Não foi possível deletar período, pois o mesmo está associado a uma Instituição");
        }
        return "redirect:/matexpress/periodoletivo/listarperiodos";
    }

}
