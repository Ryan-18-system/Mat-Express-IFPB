package br.edu.ifpb.matexpress.model.services;


import br.edu.ifpb.matexpress.model.entities.Declaracao;
import br.edu.ifpb.matexpress.model.repositories.DeclaracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeclaracaoService {
    @Autowired
    private DeclaracaoRepository declaracaoRepository;

    public String novaDeclaracao(Declaracao newDeclaracao){
        this.declaracaoRepository.save(newDeclaracao);
        return "Declaracao cadastrada com sucesso";
    }

}
