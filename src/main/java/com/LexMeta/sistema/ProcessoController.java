package com.LexMeta.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.File;

@Controller
public class ProcessoController {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @GetMapping("/novo-processo")
    public String mostrarFormulario(Model model){
        model.addAttribute("processo", new Processo());
        return "novo-processo";
    }

    @PostMapping("/novo-processo")
    public String salvarProcesso(Processo processo, @RequestParam(value = "arquivos", required = false)MultipartFile[] arquivos){
        try{
            processo.setDataCadastro(java.time.LocalDate.now());

            Processo processoSalvo = processoRepository.save(processo);

            if(arquivos != null && arquivos.length > 0){
                String pastaUpload = "uploads/";
                File pasta = new File(pastaUpload);
                if(!pasta.exists()) pasta.mkdirs();

                for(MultipartFile arquivo : arquivos){
                    if(!arquivo.isEmpty()){
                        String nomeOriginal = arquivo.getOriginalFilename();
                        String nomeUnico = System.currentTimeMillis() + "_" + nomeOriginal;
                        String caminhoCompleto = pastaUpload + nomeUnico;

                        arquivo.transferTo(new File(caminhoCompleto));

                        Documento doc = new Documento();
                        doc.setNomeArquivo(nomeOriginal);
                        doc.setCaminhoArquivo(caminhoCompleto);
                        doc.setTipo(detectarTipoDocumento(nomeOriginal));
                        doc.setTamanho(formatarTamanho(arquivo.getSize()));
                        doc.setDataEnvio(java.time.LocalDateTime.now().toString());
                        doc.setProcesso(processoSalvo);

                        documentoRepository.save(doc);
                    }
                }
            }
            return "redirect:/dashboardTelaInicial";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/novo-processo?erro=true";
        }
    }

    private String detectarTipoDocumento(String nome){
        String n = nome.toLowerCase();
        if(n.contains("peti")) return "Petição";
        if(n.contains("comprovante")) return "Comprovante";
        if(n.contains("procura")) return "Procuração";
        if(n.contains("contrato")) return "Contrato";
        if(n.contains("termo")) return "Termo";
        return "Outro";
    }

    private String formatarTamanho(Long bytes){
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return (bytes / 1024) + " KB";
        return (bytes / (1024 * 1024)) + " MB";
    }

    @GetMapping("/processos")
    public String listarProcessos
            (@RequestParam(value = "pagina", defaultValue = "1") int pagina,
             @RequestParam(value = "itensPorPagina", defaultValue = "7") int itensPorPagina,
             @RequestParam(value = "status", required = false) String status,
             @RequestParam(value = "responsavel", required = false) String responsavel,
             @RequestParam(value = "busca", required = false) String busca, Model model){
        Page<Processo> paginaProcessos;
        PageRequest pageRequest = PageRequest.of(pagina - 1, itensPorPagina, Sort.by("dataCadastro").descending());

        if (busca != null && !busca.isEmpty()) {
            paginaProcessos = processoRepository.findByNumeroProcessoContainingIgnoreCaseOrClienteContainingIgnoreCase(
                    busca, busca, pageRequest
            );
        }

        else if (status != null && !status.isEmpty() && responsavel != null && !responsavel.isEmpty()) {
            paginaProcessos = processoRepository.findByStatusAndResponsavel(status, responsavel, pageRequest);
        }

        else if (status != null && !status.isEmpty()) {
            paginaProcessos = processoRepository.findByStatus(status, pageRequest);
        }

        else if (responsavel != null && !responsavel.isEmpty()) {
            paginaProcessos = processoRepository.findByResponsavel(responsavel, pageRequest);
        }

        else {
            paginaProcessos = processoRepository.findAll(pageRequest);
        }

        long totalProcessos = processoRepository.count();
        long emAndamento = processoRepository.countByStatus("Em Andamento");
        long pendentes = processoRepository.countByStatus("Pendente");
        long concluidos = processoRepository.countByStatus("Concluído");

        model.addAttribute("processos", paginaProcessos.getContent());
        model.addAttribute("paginaAtual", pagina);
        model.addAttribute("totalPaginas", paginaProcessos.getTotalPages());
        model.addAttribute("totalProcessos", paginaProcessos.getTotalElements());
        model.addAttribute("emAndamento", emAndamento);
        model.addAttribute("pendentes", pendentes);
        model.addAttribute("concluidos", concluidos);
        model.addAttribute("itensPorPagina", itensPorPagina);
        model.addAttribute("statusFiltro", status);
        model.addAttribute("responsavelFiltro", responsavel);
        model.addAttribute("buscaFiltro", busca);

        return "processos";
    }

    @GetMapping("/processos/{id}")
    public String visualizarProcesso(@PathVariable Long id, Model model) {
        Processo processo = processoRepository.findById(id).orElse(null);
        if (processo == null) {
            return "redirect:/processos";
        }
        model.addAttribute("processo", processo);
        return "visualizar-processo";
    }

    @GetMapping("/processos/excluir/{id}")
    public String excluirProcesso(@PathVariable Long id){
        processoRepository.deleteById(id);
        return "redirect:/processos";
    }

    @GetMapping("/processos/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Processo processo = processoRepository.findById(id).orElse(null);
        if (processo == null) {
            return "redirect:/processos";
        }
        model.addAttribute("processo", processo);
        return "editar-processo";
    }

    @PostMapping("/processos/editar/{id}")
    public String salvarEdicao(@PathVariable Long id, Processo processoAtualizado) {
        Processo processo = processoRepository.findById(id).orElse(null);
        if (processo == null) {
            return "redirect:/processos";
        }

        processo.setNumeroProcesso(processoAtualizado.getNumeroProcesso());
        processo.setNumeroProcessoLivre(processoAtualizado.getNumeroProcessoLivre());
        processo.setAssuntoPrincipal(processoAtualizado.getAssuntoPrincipal());
        processo.setStatus(processoAtualizado.getStatus());
        processo.setPrioridade(processoAtualizado.getPrioridade());
        processo.setResponsavel(processoAtualizado.getResponsavel());
        processo.setVaraOrgao(processoAtualizado.getVaraOrgao());
        processo.setDataDistribuicao(processoAtualizado.getDataDistribuicao());
        processo.setValorRecebido(processoAtualizado.getValorRecebido());
        processo.setDescricao(processoAtualizado.getDescricao());

        processoRepository.save(processo);
        return "redirect:/processos";
    }
}
