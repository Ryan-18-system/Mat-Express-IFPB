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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
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
    @Transactional
    public ModelAndView cadastrarDeclaracao(@ModelAttribute("declaracao") Declaracao declaracao,
            @RequestParam("file") MultipartFile arquivo,
            ModelAndView modelAndView, RedirectAttributes redirectAttributes) throws IOException {
        String mensagemErro = verificarValidadeDatas(declaracao);
        if (mensagemErro != null) {
            redirectAttributes.addFlashAttribute("mensagemErro", mensagemErro);
            return new ModelAndView("redirect:/matexpress/declaracoes/" + declaracao.getTitular().getId());
        }
        uploadArquivo(arquivo, declaracao);
        modelAndView.setViewName("redirect:/matexpress/estudantes");
        declaracaoService.novaDeclaracao(declaracao);
        return modelAndView;
    }

    private String verificarValidadeDatas(Declaracao declaracao) {
        if (declaracao.getDataVencimento() != null && declaracao.getDataRecebimento() != null) {
            if (declaracao.getDataRecebimento().isAfter(declaracao.getDataVencimento()) || declaracao.getDataRecebimento().isEqual(declaracao.getDataVencimento())) {
                return "Data de recebimento deve ser anterior à data de vencimento";
            }
        }
        return null;
    }

    private void uploadArquivo(MultipartFile arquivo, Declaracao declaracao) throws IOException {
        String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
        Documento documento = documentoService.gravar(declaracao, nomeArquivo, arquivo.getBytes());
        documento.setUrl(buildUrl(declaracao.getId(), documento.getId()));
        declaracao.setDocumento(documento);
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

    @GetMapping("/{id}/documentos/{idDoc}")
    public ResponseEntity<byte[]> getDocumentos(@PathVariable("id") Long id, @PathVariable("idDoc") Long idDoc, ModelAndView mav) {
        Optional<Documento> documento = Optional.ofNullable(documentoService.getDocumento(idDoc));
        if (documento.isPresent()) {
            Documento doc = documento.get();
            HttpHeaders headers = new HttpHeaders();
            //mav.addObject("documento", documento.get());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", doc.getNome());

            return new ResponseEntity<>(doc.getDados(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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
