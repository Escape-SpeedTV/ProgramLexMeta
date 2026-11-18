package com.LexMeta.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PdfContratoService pdfContratoService;

    @GetMapping("/contrato")
    public String mostrarFormulario(Model model){
        model.addAttribute("contrato", new Contrato());
        return "contrato";
    }

    @PostMapping("/contrato/salvar")
    public String salvarContrato(Contrato contrato){
        contrato.setDataHoraGeracao(LocalDateTime.now());
        contrato.setDataContrato(LocalDate.now());
        contratoRepository.save(contrato);

        return "redirect:/contrato?sucesso=true";
    }

    @GetMapping("/contrato/pdf/{id}")
    public ResponseEntity<byte[]> baixarPdf(@PathVariable Long id) {
        Optional<Contrato> contratoOpt = contratoRepository.findById(id);
        if (contratoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        byte[] pdf = pdfContratoService.gerarPdf(contratoOpt.get());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "contrato_" + id + ".pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
