package br.edu.ifpb.matexpress.model.services;

import br.edu.ifpb.matexpress.model.entities.Declaracao;
import br.edu.ifpb.matexpress.model.entities.Documento;
import br.edu.ifpb.matexpress.model.repositories.DeclaracaoRepository;
import br.edu.ifpb.matexpress.model.repositories.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private DeclaracaoRepository declaracaoRepository;

    public Documento gravar(Declaracao declaracao, String nomeArquivo, byte[] bytes) throws IOException {
        Documento documento = new Documento(nomeArquivo, bytes);
        declaracao.setDocumento(documento);
        documentoRepository.save(documento);
        return documento;
    }

    public Documento getDocumento(Long id){
        return documentoRepository.findById(id).get();
    }

    public Optional<Documento> getDocumentoOf(Long idDeclaracao){
        return Optional.ofNullable(declaracaoRepository.findDocumentoById(idDeclaracao));
    }
}
