package br.edu.ifpb.matexpress.model.services;

import br.edu.ifpb.matexpress.model.entities.Declaracao;
import br.edu.ifpb.matexpress.model.repositories.DeclaracaoRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class DeclaracaoService {
    @Autowired
    private DeclaracaoRepository declaracaoRepository;

    @Transactional
    public void novaDeclaracao(Declaracao newDeclaracao) {
        this.declaracaoRepository.atualizarTodasDeclaracoesAtualParaFalse();
        this.declaracaoRepository.save(newDeclaracao);
    }
    @Transactional
    public List<Declaracao> declaracaoDoEstudante(Long id){
        return this.declaracaoRepository.getDeclaracaoByTitular(id);
    }

    @Transactional
    public ResponseEntity<byte[]> gerarPdfPorId(Long id) {
        Optional<Declaracao> declaracaoPesquisada = this.declaracaoRepository.findById(id);
        String declaracaoFormatada = this.formatadorDeMatricula(declaracaoPesquisada.get());
        return gerarDeclaracao(declaracaoFormatada);
    }

    private String formatadorDeMatricula(Declaracao declaracao) {
        StringBuilder sb = new StringBuilder();
        sb.append("Declaramos para os fins que se fizerem necessários, e por nos haver sido solicitado,")
                .append(" que ").append(declaracao.getTitular().getNome()).append(", matrícula ")
                .append(declaracao.getTitular().getMatricula()).append(", é aluno(a) regularmente matriculado(a)")
                .append(" no ").append(declaracao.getPeriodoLetivo().getPeriodo()).append(" período da Instituição ")
                .append(declaracao.getTitular().getInstituicaoAtual().getNome())
                .append(", de nível de ensino graduação, no período letivo de ")
                .append(declaracao.getPeriodoLetivo().getAno()).append(".")
                .append(declaracao.getPeriodoLetivo().getPeriodo())
                .append("\nVálido de ").append(declaracao.getDataRecebimento()).append(" à ")
                .append(declaracao.getDataVencimento());
        return sb.toString();
    }

    private ResponseEntity<byte[]> gerarDeclaracao(String declaracao) {

        Document document = new Document();
        ResponseEntity<byte[]> response = null;

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, bos);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Chunk chunk = new Chunk("DECLARAÇÃO DE MATRÍCULA", titleFont);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph(declaracao, paragraphFont));
            document.add(chapter);
            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "declaracao.pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            response = new ResponseEntity<>(bos.toByteArray(), headers, HttpStatus.OK);
            return response;
        } catch (DocumentException e) {
            System.out.println("Erro ao gerar PDF " + e.getMessage());
        }
        return response;
    }

    public List<Declaracao> obterDeclaracoesAVencer(int quantidadeDias) {
        LocalDate dataVencimento = LocalDate.now().plusDays(quantidadeDias);
        List<Declaracao> declaracoes = declaracaoRepository.findByDataVencimento(dataVencimento);
        return declaracoes;
    }

    public List<Declaracao> obterDeclaracoesVencidas() {
        LocalDate dataAtual = LocalDate.now();
        return declaracaoRepository.findByDataVencimentoMenorQue(dataAtual);
    }
}
