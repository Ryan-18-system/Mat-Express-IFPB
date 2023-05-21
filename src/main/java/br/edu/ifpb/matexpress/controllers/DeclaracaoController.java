package br.edu.ifpb.matexpress.controllers;

import br.edu.ifpb.matexpress.model.entities.Declaracao;
import br.edu.ifpb.matexpress.model.entities.Documento;
import br.edu.ifpb.matexpress.model.entities.Estudante;
import br.edu.ifpb.matexpress.model.entities.Instituicao;
import br.edu.ifpb.matexpress.model.entities.PeriodoLetivo;
import br.edu.ifpb.matexpress.model.repositories.DeclaracaoRepository;
import br.edu.ifpb.matexpress.model.services.DeclaracaoService;
import br.edu.ifpb.matexpress.model.services.DocumentoService;
import br.edu.ifpb.matexpress.model.services.EstudanteService;
import br.edu.ifpb.matexpress.model.services.InstituicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/declaracoes")
public class DeclaracaoController {

    @Autowired
    private DeclaracaoService declaracaoService;

    @Autowired
    private InstituicaoService instituicaoService;

    @Autowired
    private EstudanteService estudanteService;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private DeclaracaoRepository declaracaoRepository;

    @GetMapping()
    public ModelAndView homeDeclaracoes(ModelAndView modelAndView) {
        modelAndView.setViewName("declaracoes/listagem");
        return modelAndView;
    }

    @PostMapping("salvar")
    public ModelAndView cadastrarDeclaracao(Declaracao declaracao, ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/matexpress/estudantes");
        declaracaoService.novaDeclaracao(declaracao);
        return modelAndView;
    }

    @GetMapping("gerar-pdf/{id}")
    public ResponseEntity<byte[]> gerarPdf(@PathVariable("id") Long id) {
        return this.declaracaoService.gerarPdfPorId(id);
    }

    @GetMapping("/{id}")
    public ModelAndView formDeclaracoes(ModelAndView modelAndView, Declaracao declaracao, @PathVariable("id") Long id) {
        Estudante estudantePesquisado = this.getEstudante(id);
        Instituicao instituicao = estudantePesquisado.getInstituicaoAtual();
        modelAndView.setViewName("declaracoes/form");
        modelAndView.addObject("declaracao", declaracao);
        modelAndView.addObject("estudantePesquisado", estudantePesquisado);
        modelAndView.addObject("instituicao", instituicao);
        modelAndView.addObject("periodos", this.listarPeriodosDaIntituicao(instituicao.getId()));
        return modelAndView;
    }

    @GetMapping("/gerar-relatorios")
    public ModelAndView declaracoesAVencer(ModelAndView modelAndView) {
        modelAndView.setViewName("declaracoes/gerar-relatorios");
        return modelAndView;
    }

    @GetMapping("/relatorio")
    public ModelAndView relatoriosDeclaracoes(@RequestParam("dias") int quantidadeDias, ModelAndView modelAndView) {
        modelAndView.setViewName("declaracoes/relatorio");
        modelAndView.addObject("declaracoesAVencer", this.declaracoesRelatorio(quantidadeDias));
        return modelAndView;
    }

    @GetMapping("/relatorio-vencidas")
    public ModelAndView relatorioDeclaracoesVencidas(ModelAndView modelAndView) {
        modelAndView.setViewName("declaracoes/relatorio-vencidas");
        modelAndView.addObject("declaracoesVencidas", this.declaracoesVencidas());
        return modelAndView;
    }

    @GetMapping("/{id}/documentos/form")
    public ModelAndView getForm(ModelAndView mav, @PathVariable(name = "id") Long id) {
        mav.addObject("id", id);
        mav.setViewName("declaracoes/documentos/form");
        return mav;
    }

    @GetMapping("/{id}/documentos")
    public ModelAndView getDocumentos(@PathVariable("id") Long id, ModelAndView mav) {
        Optional<Documento> documento = documentoService.getDocumentoOf(id);
        if (documento.isPresent()) {
            mav.addObject("documento", documento.get());
        }
        mav.setViewName("declaracoes/documentos/list");
        return mav;
    }

    @RequestMapping(value = "/{id}/documentos/upload", method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile arquivo,
            @PathVariable("id") Long id, ModelAndView mav) {
        String mensagem = "";
        String proxPagina = "";
        try {
            Optional<Declaracao> opDeclaracao = declaracaoRepository.findById(id);
            Declaracao declaracao = null;
            if (opDeclaracao.isPresent()) {
                declaracao = opDeclaracao.get();
                String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
                Documento documento = documentoService.gravar(declaracao, nomeArquivo, arquivo.getBytes());
                documento.setUrl(this.buildUrl(declaracao.getId(), documento.getId()));
                declaracaoRepository.save(declaracao);
                mensagem = "Documento carregado com sucesso: " + arquivo.getOriginalFilename();
                proxPagina = String.format("redirect:/declaracoes/%s/documentos", declaracao.getId().toString());
            }
        } catch (Exception e) {
            mensagem = "Não foi possível carregar o documento: " + arquivo.getOriginalFilename() + "! "
                    + e.getMessage();
            proxPagina = "/declaracoes/documentos/form";
        }
        mav.addObject("mensagem", mensagem);
        mav.setViewName(proxPagina);
        return mav;
    }

    private String buildUrl(Long idDeclaracao, Long idDocumento) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/declaracoes/")
                .path(idDeclaracao.toString())
                .path("/documentos/")
                .path(idDocumento.toString())
                .toUriString();
        return fileDownloadUri;
    }


    private List<PeriodoLetivo> listarPeriodosDaIntituicao(Long id) {
        return this.instituicaoService.listarPeriodosDaInstituicao(id);
    }

    private Estudante getEstudante(Long id) {
        return this.estudanteService.pesquisarPorId(id);
    }

    public List<Declaracao> declaracoesRelatorio(int dias) {
        return declaracaoService.obterDeclaracoesAVencer(dias);
    }

    public List<Declaracao> declaracoesVencidas() {
        return declaracaoService.obterDeclaracoesVencidas();
    }

}
