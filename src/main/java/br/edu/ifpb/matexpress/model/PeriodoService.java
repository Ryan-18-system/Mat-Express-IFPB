package br.edu.ifpb.matexpress.model;

import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.repositories.PeriodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PeriodoService {
    @Autowired
    private PeriodoRepository periodoRepository;

    public void cadastrarPeriodo(PeriodoLetivo newPeriodo){
        this.periodoRepository.save(newPeriodo);
    }

    public List<PeriodoLetivo> listarPeriodos(){
        return this.periodoRepository.findAll();
    }
}
