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

        Optional<Meta> metaDoMes = metaRepository.findByMesAndAno(mesAtual, anoAtual);
        int quantidadeMetaAtual = metaDoMes.map(Meta::getQuantidade).orElse(0);

        List<Processo> processosConcluidosList = processoRepository.findAllByStatus("Concluído");
        int[] concluidosMensal = new int[12];
        for(Processo p : processosConcluidosList) {
            if (p.getDataCadastro() != null) {
                int mes = p.getDataDistribuicao().getMonthValue() - 1;
                concluidosMensal[mes]++;
            }
        }
        int concluidosMesAtual = concluidosMensal[mesAtual - 1];
        long percentual = (quantidadeMetaAtual > 0) ? (concluidosMesAtual * 100 / quantidadeMetaAtual) : 0;
        long faltamParaMeta = (quantidadeMetaAtual - concluidosMesAtual) > 0 ? (quantidadeMetaAtual - concluidosMesAtual) : 0;

        int[] alcancado = new int[12];
        int[] metaEvolucao = new int[12];

        int somaConcluidos = 0;
        int somaMetas = 0;

        for(int i = 0; i < 12; i++){
            somaConcluidos += concluidosMensal[i];
            somaMetas += metaMensal[i];

            if(i <= (mesAtual - 1)){
                alcancado[i] += somaConcluidos;
                metaEvolucao[i] += somaMetas;
            }else{
                alcancado[i] = 0;
                metaEvolucao[i] = 0;
            }
        }

        int metaTotalAno = 0;
        for(int m : metaMensal){
            metaTotalAno += m;
        }
        long meta = metaTotalAno;

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

        String[] nomesMeses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        model.addAttribute("alcancado", alcancado);
        model.addAttribute("metaEvolucao", metaEvolucao);
        return "dashboardTelaInicial";
    }
}