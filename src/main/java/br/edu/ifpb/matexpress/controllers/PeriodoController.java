package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.PeriodoService;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("listagemperiodos")
    public String cadastrarPeriodoLetivo(Model model, @Valid PeriodoLetivo periodoLetivo){
        periodoService.cadastrarPeriodo(periodoLetivo);
        model.addAttribute("periodos", periodoService.listarPeriodos());
        return "periodoletivo/listagem";
    }

}
