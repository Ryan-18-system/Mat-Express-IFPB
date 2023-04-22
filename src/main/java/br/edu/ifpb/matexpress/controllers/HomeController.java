package br.edu.ifpb.matexpress.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String showHomePage(Model mav) {
        mav.addAttribute("menu", "home");
        return "index";
    }
}