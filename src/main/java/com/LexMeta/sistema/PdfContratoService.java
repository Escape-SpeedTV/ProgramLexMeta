package com.LexMeta.sistema;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PdfContratoService {

    private static final Pattern IMG_SRC = Pattern.compile("src=\"(/imagens/[^\"]+)\"");
    private static final Pattern LINK_CSS = Pattern.compile("<link[^>]*contratoPdf\\.css[^>]*>");

    public byte[] gerarPdf(Contrato contrato) {
        try {
            String html = lerTexto("templates/contrato-template.html");

            String css = lerTexto("static/css/contratoPdf.css");
            html = LINK_CSS.matcher(html).replaceAll(
                    Matcher.quoteReplacement("<style type=\"text/css\">\n" + css + "\n</style>"));

            String dataHora = contrato.getDataHoraGeracao() != null
                    ? contrato.getDataHoraGeracao().format(DateTimeFormatter.ofPattern(
                    "dd 'de' MMMM 'de' yyyy 'às' HH:mm:ss", new Locale("pt", "BR")))
                    : "";

            html = sub(html, "nome", contrato.getNome());
            html = sub(html, "nacionalidade", contrato.getNacionalidade());
            html = sub(html, "profissao", contrato.getProfissao());
            html = sub(html, "estadoCivil", contrato.getEstadoCivil());
            html = sub(html, "cpf", contrato.getCpf());
            html = sub(html, "rg", contrato.getRg());
            html = sub(html, "orgaoEmissor", contrato.getOrgaoEmissor());
            html = sub(html, "endereco", contrato.getEndereco());
            html = sub(html, "cep", contrato.getCep());
            html = sub(html, "numeroContrato", contrato.getNumeroContrato());
            html = sub(html, "numeroTermoReserva", contrato.getNumeroTermoReserva());
            html = sub(html, "enderecoImovel", contrato.getEnderecoImovel());
            html = sub(html, "local", contrato.getLocal());
            html = sub(html, "dataHora", dataHora);

            Matcher m = IMG_SRC.matcher(html);
            StringBuilder sb = new StringBuilder();
            while (m.find()) {
                String src = "src=\"" + dataUri(m.group(1)) + "\"";
                m.appendReplacement(sb, Matcher.quoteReplacement(src));
            }
            m.appendTail(sb);
            html = sb.toString();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(baos);
            return baos.toByteArray();

        } catch (Exception e) {
            throw new IllegalStateException("Erro ao gerar o PDF do contrato", e);
        }
    }

    private String sub(String html, String chave, String valor) {
        return html.replace("{{" + chave + "}}", HtmlUtils.htmlEscape(valor != null ? valor : ""));
    }

    private String lerTexto(String caminhoClasspath) throws IOException {
        ClassPathResource r = new ClassPathResource(caminhoClasspath);
        if (!r.exists()) {
            throw new IllegalStateException("Arquivo não encontrado no classpath: " + caminhoClasspath);
        }
        try (InputStream in = r.getInputStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private String dataUri(String caminhoWeb) throws IOException {
        String caminho = "static" + caminhoWeb;
        ClassPathResource r = new ClassPathResource(caminho);
        if (!r.exists()) {
            throw new IllegalStateException("Imagem não encontrada no classpath: " + caminho);
        }
        String lower = caminho.toLowerCase(Locale.ROOT);
        String mime = lower.endsWith(".png") ? "image/png" : "image/jpeg";
        try (InputStream in = r.getInputStream()) {
            return "data:" + mime + ";base64," + Base64.getEncoder().encodeToString(in.readAllBytes());
        }
    }
}