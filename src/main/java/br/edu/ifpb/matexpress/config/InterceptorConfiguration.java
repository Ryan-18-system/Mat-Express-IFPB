package br.edu.ifpb.matexpress.config;

import br.edu.ifpb.matexpress.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(authInterceptor)
                .addPathPatterns("/**", "/estudantes/**", "/insituicoes/**", "/periodoletivo/**", "/home/**")
                .excludePathPatterns("/auth/**", "/css/**", "/imagens/**");
    }

}
