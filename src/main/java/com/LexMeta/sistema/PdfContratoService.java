package com.LexMeta.sistema;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfContratoService {
    public byte[] gerarPdf(Contrato contrato) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, baos);
            document.open();
            Font tituloFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("CONTRATO DE PRESTAÇÃO DE SERVIÇOS ADVOCATÍCIOS", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" "));

            Font textoFont = new Font(Font.HELVETICA, 11);

            Paragraph contratante = new Paragraph(
                    "CONTRATANTE: " + contrato.getNome() + ", " + contrato.getNacionalidade() + ", "
                            + contrato.getProfissao() + ", " + contrato.getEstadoCivil() + ", portador(a) do CPF nº "
                            + contrato.getCpf() + " e do RG nº " + contrato.getRg() + " " + contrato.getOrgaoEmissor()
                            + ", residente e domiciliado(a) em " + contrato.getEndereco() + ", CEP " + contrato.getCep() + ".",
                    textoFont);
            contratante.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(contratante);
            document.add(new Paragraph(" "));

            Paragraph contratado = new Paragraph(
                    "CONTRATADO: DOUGLAS PALATUCCI DA SILVA, brasileiro, advogado, inscrito na OAB/DF sob o n.º 86.224.",
                    textoFont);
            contratado.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(contratado);
            document.add(new Paragraph(" "));

            Paragraph objeto = new Paragraph(
                    "I.I – DO OBJETO: O presente contrato tem por objeto a prestação de serviços advocatícios " +
                            "para o processo referente ao Contrato de Compra e Venda n. " + contrato.getNumeroContrato() +
                            " e Termo de Reserva n. " + contrato.getNumeroTermoReserva() + ", referente ao imóvel " +
                            contrato.getEnderecoImovel() + ".",
                    textoFont);
            objeto.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(objeto);
            document.add(new Paragraph(" "));

            String dataHora = contrato.getDataHoraGeracao() != null
                    ? contrato.getDataHoraGeracao().format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy 'às' HH:mm:ss"))
                    : "Data não informada";

            Paragraph local = new Paragraph(contrato.getLocal() + ", " + dataHora, textoFont);
            local.setAlignment(Element.ALIGN_LEFT);
            document.add(local);
            document.add(new Paragraph(" "));

            Paragraph assinatura = new Paragraph(
                    "\n\n_____________________________________\n" +
                            contrato.getNome() + "\nCPF: " + contrato.getCpf() + "\n\n\n" +
                            "_____________________________________\n" +
                            "DOUGLAS PALATUCCI DA SILVA\nOAB-DF 86.224",
                    textoFont);
            document.add(assinatura);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }
}