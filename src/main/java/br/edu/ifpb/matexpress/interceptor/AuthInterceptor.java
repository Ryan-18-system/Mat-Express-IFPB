package br.edu.ifpb.matexpress.interceptor;

import br.edu.ifpb.matexpress.model.entities.Estudante;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {
        boolean allowed = true;
        HttpSession httpSession = request.getSession(false);

        if (httpSession != null
                && ((Estudante) httpSession.getAttribute("usuario")) != null) {
            allowed = true;
        } else {
            String baseUrl = "/matexpress"; // nome da aplicação
            String paginaLogin = baseUrl + "/auth";
            response.sendRedirect(response.encodeRedirectURL(paginaLogin));
           allowed = false;
        }
        return allowed;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // Sem uso
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // Sem uso
    }
}
