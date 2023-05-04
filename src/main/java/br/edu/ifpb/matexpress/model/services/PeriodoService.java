package br.edu.ifpb.matexpress.model.services;

import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.repositories.PeriodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class PeriodoService {
    @Autowired
    private PeriodoRepository periodoRepository;

    @Transactional
    public void cadastrarPeriodo(PeriodoLetivo newPeriodo) {
        this.periodoRepository.save(newPeriodo);
    }

    public List<PeriodoLetivo> listarPeriodos() {
        return this.periodoRepository.findAll();
    }

    public PeriodoLetivo pesquisarPeriodoPorId(Long idPeriodo) {
        Optional<PeriodoLetivo> periodoBanco = periodoRepository.findById(idPeriodo);
        return periodoBanco.orElseGet(periodoBanco::orElseThrow);
    }
    public void deletarPorId(Long idPeriodo) throws Exception{
            PeriodoLetivo periodoBanco = this.pesquisarPeriodoPorId(idPeriodo);
            this.periodoRepository.delete(periodoBanco);

    }

}
