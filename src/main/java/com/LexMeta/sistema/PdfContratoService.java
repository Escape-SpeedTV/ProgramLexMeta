package com.LexMeta.sistema;

import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
public class PdfContratoService {

    public byte[] gerarPdf(Contrato contrato) {
        try {
            String templatePath = "src/main/resources/templates/contrato-template.html";
            String html = new String(Files.readAllBytes(Paths.get(templatePath)));

            String dataHora = contrato.getDataHoraGeracao() != null
                    ? contrato.getDataHoraGeracao().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'às' HH:mm:ss"))
                    : "";

            html = html.replace("{{nome}}", nvl(contrato.getNome()));
            html = html.replace("{{nacionalidade}}", nvl(contrato.getNacionalidade()));
            html = html.replace("{{profissao}}", nvl(contrato.getProfissao()));
            html = html.replace("{{estadoCivil}}", nvl(contrato.getEstadoCivil()));
            html = html.replace("{{cpf}}", nvl(contrato.getCpf()));
            html = html.replace("{{rg}}", nvl(contrato.getRg()));
            html = html.replace("{{orgaoEmissor}}", nvl(contrato.getOrgaoEmissor()));
            html = html.replace("{{endereco}}", nvl(contrato.getEndereco()));
            html = html.replace("{{cep}}", nvl(contrato.getCep()));
            html = html.replace("{{numeroContrato}}", nvl(contrato.getNumeroContrato()));
            html = html.replace("{{numeroTermoReserva}}", nvl(contrato.getNumeroTermoReserva()));
            html = html.replace("{{enderecoImovel}}", nvl(contrato.getEnderecoImovel()));
            html = html.replace("{{local}}", nvl(contrato.getLocal()));
            html = html.replace("{{dataHora}}", dataHora);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();

            renderer.getSharedContext().setUserAgentCallback(
                    new ITextUserAgent(renderer.getOutputDevice()) {
                        @Override
                        public String resolveURI(String uri) {
                            if (uri.startsWith("/css/")) {
                                return new File("src/main/resources/static" + uri).toURI().toString();
                            }
                            if (uri.startsWith("/imagens/")) {
                                return new File("src/main/resources/static" + uri).toURI().toString();
                            }
                            return super.resolveURI(uri);
                        }
                    }
            );

            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(baos);

            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private String nvl(String valor) {
        return valor != null ? valor : "";
    }
}