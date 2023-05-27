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

    @Autowired
    private EstudanteRepository estudanteRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/login");
        modelAndView.addObject("usuario", new Estudante());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView valide(Estudante estudante, HttpSession session, ModelAndView modelAndView,
                               RedirectAttributes redirectAttts) {
        if ((estudante = this.isValido(estudante)) != null) {
            session.setAttribute("usuario", estudante);
            modelAndView.setViewName("redirect:/home");
        } else {
            redirectAttts.addFlashAttribute("mensagem", "Login e/ou senha inválidos!");
            modelAndView.setViewName("redirect:/matexpress/auth");
        }
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        session.invalidate();
        mav.setViewName("redirect:/matexpress/auth");
        return mav;
    }

    private Estudante isValido(Estudante estudante) {
        Estudante estudanteBanco = estudanteRepository.findByMatricula(estudante.getMatricula());
        boolean valido = true;
        if (estudanteBanco != null) {
            if (PasswordUtil.checkPass(estudante.getSenha(), estudanteBanco.getSenha())) {
                valido = true;
            }
        }
        return valido ? estudanteBanco : null;
    }
}

