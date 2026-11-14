package com.LexMeta.sistema;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PdfService {

    public Processo extrairDadosDoPdf(File arquivoPdf) throws Exception{
        Processo processo = new Processo();

        try(PDDocument documento = Loader.loadPDF(arquivoPdf)){
            PDFTextStripper stripper = new PDFTextStripper();
            String texto = stripper.getText(documento);

            System.out.println("=== TEXTO DO PDF ===\n" + texto + "\n=========");

            Pattern patternNome = Pattern.compile("(?:OUTORGANTE|CONTRATANTE):\\s*([A-ZÀ-Ú][A-ZÀ-Ú\\s]+)(?=,|\\s*CPF|\\s*brasileir)");

            Matcher matcherNome = patternNome.matcher(texto);
            if(matcherNome.find()){
                processo.setCliente(matcherNome.group(1).trim());
            }else{
                Pattern patternNomeFallback = Pattern.compile(
                        "([A-ZÀ-Ú][A-ZÀ-Ú\\\\s]{5,}?)\\\\s*(?:CPF|,)"
                );
                Matcher matcherNomeFallBack = patternNomeFallback.matcher(texto);
                if(matcherNomeFallBack.find()){
                    processo.setCliente(matcherNomeFallBack.group(1).trim());
                }
            }

            Pattern patternCpf = Pattern.compile("(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})");
            Matcher matcherCpf = patternCpf.matcher(texto);
            if (matcherCpf.find()) {
                processo.setCpf(matcherCpf.group(1));
                System.out.println("CPF encontrado: " + matcherCpf.group(1));
            }

            Pattern patternContrato = Pattern.compile("(?:Contrato|Termo)\\s+(?:de\\s+\\w+\\s+)?(?:n\\.?[º°]?|nº)?\\s*([\\d.]+)");
            Matcher matcherContrato = patternContrato.matcher(texto);
            if (matcherContrato.find()) {
                processo.setNumeroProcessoLivre(matcherContrato.group(1).trim());
            }

            Pattern patternEndereco = Pattern.compile("residente\\s+e\\s+domiciliad[ao]\\s+n[ao]\\s+(.+?)(?=\\.|,|\\s*CEP)");
            Matcher matcherEndereco = patternEndereco.matcher(texto);
            if (matcherEndereco.find()) {
                System.out.println("Endereço encontrado: " + matcherEndereco.group(1).trim());
            }

            Pattern patternAdvogado = Pattern.compile(
                    "OUTORGADO:\\s*([A-ZÀ-Ú][A-ZÀ-Ú\\s]+?)(?=,|\\s*brasileir|\\s*advogado)");
            Matcher matcherAdvogado = patternAdvogado.matcher(texto);
            if(matcherAdvogado.find()){
                processo.setAdvogado(matcherAdvogado.group(1).trim());
                System.out.println("Advogado encontrado: " + matcherAdvogado.group(1).trim());
            }

            Pattern patternOab = Pattern.compile("OAB/([A-Z]{2})\\s*n[º°]?\\s*(\\d+\\.?\\d*)");
            Matcher matcherOab = patternOab.matcher(texto);
            if (matcherOab.find()) {
                processo.setOab(matcherOab.group(1) + " " + matcherOab.group(2));
                System.out.println("OAB encontrada: " + matcherOab.group(1) + " " + matcherOab.group(2));
            }

            if (texto.toLowerCase().contains("imóvel") || texto.toLowerCase().contains("imobiliário")) {
                processo.setAssuntoPrincipal("Direito Imobiliário");
            }
        }

        return processo;
    }
}
