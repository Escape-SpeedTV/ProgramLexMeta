package com.LexMeta.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProcessoController {

    @Autowired
    private ProcessoRepository processoRepository;

    @GetMapping("/novo-processo")
    public String mostrarFormulario(Model model){
        model.addAttribute("processo", new Processo());
        return "novo-processo";
    }

    @PostMapping("/novo-processo")
    public String salvarProcesso(Processo processo){
        processo.setDataCadastro(java.time.LocalDate.now());
        processoRepository.save(processo);
        return "redirect:/dashboardTelaInicial";
    }

    @GetMapping("/processos")
    public String listarProcessos
            (@RequestParam(value = "pagina", defaultValue = "1") int pagina,
             @RequestParam(value = "itensPorPagina", defaultValue = "7") int itensPorPagina,
             @RequestParam(value = "status", required = false) String status, Model model){
        Page<Processo> paginaProcessos;

        if (status != null && !status.isEmpty()) {
            paginaProcessos = processoRepository.findByStatus(status, PageRequest.of(pagina - 1, itensPorPagina, Sort.by("dataCadastro").descending())
            );
        } else {
            paginaProcessos = processoRepository.findAll(PageRequest.of(pagina - 1, itensPorPagina, Sort.by("dataCadastro").descending())
            );
        }

        long totalProcessos = processoRepository.count();
        long emAndamento = processoRepository.countByStatus("Em Andamento");
        long pendentes = processoRepository.countByStatus("Pendente");
        long concluidos = processoRepository.countByStatus("Concluído");

        model.addAttribute("processos", paginaProcessos.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", paginaProcessos.getTotalPages());
        model.addAttribute("totalProcessos", totalProcessos);
        model.addAttribute("emAndamento", emAndamento);
        model.addAttribute("pendentes", pendentes);
        model.addAttribute("concluidos", concluidos);
        model.addAttribute("itensPorPagina", itensPorPagina);
        model.addAttribute("statusFiltro", status);

        return "processos";
    }
}
