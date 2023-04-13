package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.services.PeriodoService;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/periodoletivo")
public class PeriodoController {
    @Autowired
    private PeriodoService periodoService;
    @GetMapping("/form")
    public String formPeriodo(PeriodoLetivo periodoLetivo, Model model){
        model.addAttribute("periodoLetivo", periodoLetivo);
        return "periodoletivo/form";
    }
    @PostMapping("**/salvarperiodo")
    public ModelAndView cadastrarPeriodoLetivo(@Valid PeriodoLetivo periodoLetivo, BindingResult result){
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("periodoletivo/form");
            return mv;
        } else {
            periodoService.cadastrarPeriodo(periodoLetivo);
            return new ModelAndView("redirect:/matexpress/periodoletivo/listarperiodos");
        }
    }

    @GetMapping("**/listarperiodos")
    public ModelAndView listarPeriodos(ModelAndView modelAndView){
        modelAndView.setViewName("periodoletivo/listagem");
        return modelAndView;
    }

    @ModelAttribute("periodos")
    public List<PeriodoLetivo> periodoLetivos(){
        return  periodoService.listarPeriodos();
    }

    @GetMapping("/editarperiodo/{idPeriodo}")
    public ModelAndView editarPeriodo(ModelAndView modelAndView, @PathVariable("idPeriodo") Long idPeriodo){
        modelAndView.setViewName("periodoletivo/form");
        modelAndView.addObject("periodoLetivo", periodoService.pesquisarPeriodoPorId(idPeriodo));
        return modelAndView;
    }
    @GetMapping("/removerperiodo/{idPeriodo}")
    public String removerPeriodo(@PathVariable("idPeriodo") Long idPeriodo){
        periodoService.deletarPorId(idPeriodo);
        return "redirect:/matexpress/periodoletivo/listarperiodos";
    }

}
