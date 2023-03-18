package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.repositories.PeriodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/periodoLetivo")
public class PeriodoController {
    @Autowired
    private PeriodoRepository periodoRepository;
    @RequestMapping("/form")
    public String formPeriodo(PeriodoLetivo periodoLetivo, Model model){
        model.addAttribute("periodo", periodoLetivo);
        return "periodos/formPeriodo";
    }
    @PostMapping("/listagemDePeriodos")
    public String cadastrarPeriodoLetivo(Model model, PeriodoLetivo periodoLetivo){
        periodoRepository.save(periodoLetivo);
        model.addAttribute("periodos", periodoRepository.findAll());
        return "periodos/listaDePeriodos";
    }
}
