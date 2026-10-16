package com.LexMeta.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class  DashboardController {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private MetaRepository metaRepository;

    @GetMapping("/dashboardTelaInicial")
    public String mostrarDashboard(Model model){
        long totalProcessos = processoRepository.count();
        long concluidos = processoRepository.countByStatus("Concluído");
        long emAndamento = processoRepository.countByStatus("Em Andamento");
        long pendentes = processoRepository.countByStatus("Pendente");

        int mesAtual = java.time.LocalDate.now().getMonthValue();
        int anoAtual = java.time.LocalDate.now().getYear();

        int[] metaMensal = new int[12];
        List<Meta> metasdoAno = metaRepository.findByAnoOrderByMesAsc(anoAtual);
        for(Meta m : metasdoAno){
            metaMensal[m.getMes() - 1] = m.getQuantidade();
        }

        int metaTotalAno = 0;
        for(int m : metaMensal){
            metaTotalAno += m;
        }
        long meta = metaTotalAno;
        long percentual = (meta > 0) ? (concluidos * 100 / meta) : 0;
        long faltamParaMeta = (meta - totalProcessos) > 0 ? (meta - totalProcessos) : 0;

        Optional<Meta> metaDoMes = metaRepository.findByMesAndAno(mesAtual, anoAtual);
        int quantidadeMetaAtual = metaDoMes.map(Meta::getQuantidade).orElse(0);


        List<Processo> processosConcluidosList = processoRepository.findAllByStatus("Concluído");
        int[] concluidosMensal = new int[12];

        for(Processo p : processosConcluidosList){
            if (p.getDataCadastro() != null) {
                int mes = p.getDataCadastro().getMonthValue() - 1;
                concluidosMensal[mes]++;
            }
        }

        double valorTotal = 0.0;
        int processoscomValor = 0;
        for(Processo p : processosConcluidosList){
            if(p.getValorRecebido() != null && !p.getValorRecebido().isEmpty()){
                try{
                    String valorLimpo = p.getValorRecebido().replace("R$","").replace(".", "").replace(",", ".").trim();

                    double valor = Double.parseDouble(valorLimpo);
                    valorTotal += valor;
                    processoscomValor++;
                }catch (NumberFormatException e){
                    System.out.println(e);
                }
            }
        }

        long processosConcluidos = processoRepository.countByStatus("Concluído");
        double mediaPorProcesso = (processosConcluidos > 0) ? (valorTotal / processosConcluidos) : 0.0;

        double ticketMedioGeral = (totalProcessos > 0) ? (valorTotal / totalProcessos) : 0.0;

        java.text.NumberFormat formatoBR = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));


        model.addAttribute("metaTotal", meta);
        model.addAttribute("percentualConcluido", percentual);
        model.addAttribute("processosConcluidos", concluidos);
        model.addAttribute("emAndamento", emAndamento);
        model.addAttribute("pendentes", pendentes);
        model.addAttribute("faltamParaMeta", faltamParaMeta);

        model.addAttribute("valorTotalRecebido", formatoBR.format(valorTotal));
        model.addAttribute("mediaPorProcesso", formatoBR.format(mediaPorProcesso));
        model.addAttribute("ticketMedioGeral", formatoBR.format(ticketMedioGeral));

        model.addAttribute("meses", new String[]{"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"});
        model.addAttribute("concluidosMensal", concluidosMensal);
        model.addAttribute("metaMensal", metaMensal);

        model.addAttribute("mesAtual", mesAtual);
        model.addAttribute("anoAtualModal", anoAtual);
        model.addAttribute("quantidadeMetaAtual", quantidadeMetaAtual);

        String[] nomesMeses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        model.addAttribute("nomeMesAtual", nomesMeses[mesAtual - 1]);

        model.addAttribute("alcancado", new int[]{20, 25, 40, 55, 70, 65, 80, 85, 90, 75, 95, 100});
        model.addAttribute("metaEvolucao", new int[]{40, 40, 50, 60, 70, 70, 85, 90, 95, 95, 100, 105});
        return "dashboardTelaInicial";
    }
}