package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.services.PeriodoService;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/periodoletivo")
public class PeriodoController {
    @Autowired
    private PeriodoService periodoService;
    @GetMapping("/form")
    public String formPeriodo(PeriodoLetivo periodoLetivo, Model model){
        model.addAttribute("periodo", periodoLetivo);
        return "periodoletivo/form";
    }
    @PostMapping("**/salvarperiodo")
    public String cadastrarPeriodoLetivo(Model model, PeriodoLetivo periodoLetivo){
        periodoService.cadastrarPeriodo(periodoLetivo);
        model.addAttribute("periodos", periodoService.listarPeriodos());
        return "periodoletivo/listagem";
    }
    @GetMapping("/listarperiodos")
    public ModelAndView listarPeriodos(ModelAndView modelAndView){
        modelAndView.setViewName("periodoletivo/listagem");
        modelAndView.addObject("periodos", periodoService.listarPeriodos());
        return modelAndView;
    }

    @GetMapping("/editarperiodo/{idPeriodo}")
    public ModelAndView editarPeriodo(ModelAndView modelAndView, @PathVariable("idPeriodo") Long idPeriodo){
        modelAndView.setViewName("periodoletivo/form");
        modelAndView.addObject("periodo", periodoService.pesquisarPeriodoPorId(idPeriodo));
        return modelAndView;
    }

}
