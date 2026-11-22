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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ContratoController {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private PdfContratoService pdfContratoService;

    @GetMapping("/contratos")
    public String listarContratos(Model model) {
        model.addAttribute("contratos", contratoRepository.findAll());
        return "contratos";
    }

    @GetMapping("/contrato")
    public String mostrarFormulario(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            Optional<Contrato> contratoOpt = contratoRepository.findById(id);
            if (contratoOpt.isPresent()) {
                model.addAttribute("contrato", contratoOpt.get());
                return "contrato";
            }
        }
        model.addAttribute("contrato", new Contrato());
        return "contrato";
    }

    @GetMapping("/contrato/excluir/{id}")
    public String excluirContrato(@PathVariable Long id){
        contratoRepository.deleteById(id);
        return "redirect:/contratos?sucesso=true";
    }

    @GetMapping("/contrato/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Optional<Contrato> contratoOpt = contratoRepository.findById(id);
        if (contratoOpt.isEmpty()) {
            return "redirect:/contratos";
        }
        model.addAttribute("contrato", contratoOpt.get());
        return "contrato";
    }

    @PostMapping("/contrato/editar/{id}")
    public String salvarEdicao(@PathVariable Long id, Contrato contratoAtualizado) {
        Optional<Contrato> contratoOpt = contratoRepository.findById(id);
        if (contratoOpt.isEmpty()) {
            return "redirect:/contratos";
        }
        Contrato contrato = contratoOpt.get();
        contrato.setNome(contratoAtualizado.getNome());
        contrato.setNacionalidade(contratoAtualizado.getNacionalidade());
        contrato.setProfissao(contratoAtualizado.getProfissao());
        contrato.setEstadoCivil(contratoAtualizado.getEstadoCivil());
        contrato.setCpf(contratoAtualizado.getCpf());
        contrato.setRg(contratoAtualizado.getRg());
        contrato.setOrgaoEmissor(contratoAtualizado.getOrgaoEmissor());
        contrato.setEndereco(contratoAtualizado.getEndereco());
        contrato.setCep(contratoAtualizado.getCep());
        contrato.setNumeroContrato(contratoAtualizado.getNumeroContrato());
        contrato.setNumeroTermoReserva(contratoAtualizado.getNumeroTermoReserva());
        contrato.setEnderecoImovel(contratoAtualizado.getEnderecoImovel());
        contrato.setLocal(contratoAtualizado.getLocal());
        contratoRepository.save(contrato);
        return "redirect:/contratos?sucesso=true";
    }


    @PostMapping("/contrato/salvar")
    public String salvarContrato(Contrato contrato){
        contrato.setDataHoraGeracao(LocalDateTime.now());
        contrato.setDataContrato(LocalDate.now());
        Contrato contratoSalvo = contratoRepository.save(contrato);

        return "redirect:/contrato?sucesso=true&id=" + contratoSalvo.getId();
    }

    @GetMapping("/contrato/pdf/{id}")
    public ResponseEntity<byte[]> baixarPdf(@PathVariable Long id) {
        Optional<Contrato> contratoOpt = contratoRepository.findById(id);
        if (contratoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Contrato contrato = contratoOpt.get();

        byte[] pdf = pdfContratoService.gerarPdf(contratoOpt.get());

        String nomeCliente = contrato.getNome().replaceAll("[^a-zA-Z0-9]", "_").replaceAll("_+", "_");
        String nomeArquivo = "contrato_" + nomeCliente + ".pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", nomeArquivo);

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
