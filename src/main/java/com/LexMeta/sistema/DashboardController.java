package com.LexMeta.sistema;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/dashboardTelaInicial")
    public String mostrarDashboard(Model model){
        model.addAttribute("metaTotal", 120);
        model.addAttribute("percentualConcluido", 68);
        model.addAttribute("processosConcluidos", 82);
        model.addAttribute("emAndamento", 23);
        model.addAttribute("pendentes", 15);

        model.addAttribute("faltamParaMeta", 38);
        model.addAttribute("valorTotalRecebido", "R$248.750,00");
        model.addAttribute("mediaPorProcesso", "R$3.033,54");
        model.addAttribute("ticketMedioGeral", "R$2.281,65");

        model.addAttribute("meses", new String[]{"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"});
        model.addAttribute("concluidosMensalmente", new int[]{30, 45, 40, 60, 35, 70, 68, 50, 60, 40, 75, 55});
        model.addAttribute("metaMensal", new int[]{25, 35, 50, 45, 55, 50, 65, 70, 40, 60, 50, 45});

        model.addAttribute("alcancado", new int[]{20, 25, 40, 55, 70, 65, 80, 85, 90, 75, 95, 100});
        model.addAttribute("metaEvolucao", new int[]{40, 40, 50, 60, 70, 70, 85, 90, 95, 95, 100, 105});
        return "dashboardTelaInicial";
    }
}