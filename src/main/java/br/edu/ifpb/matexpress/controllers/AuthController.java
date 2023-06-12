package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.entities.Estudante;
import br.edu.ifpb.matexpress.model.repositories.EstudanteRepository;
import br.edu.ifpb.matexpress.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("matexpress/auth");
        modelAndView.addObject("usuario", new Estudante());
        return modelAndView;
    }


    @RequestMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        session.invalidate();
        mav.setViewName("redirect:/matexpress/auth");
        return mav;
    }
}

