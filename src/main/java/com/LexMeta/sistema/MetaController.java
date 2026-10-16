package com.LexMeta.sistema;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MetaController {
    @Autowired
    private MetaRepository metaRepository;

    @GetMapping("/metas")
    public String listarMetas(Model model) {
        int anoAtual = java.time.LocalDate.now().getYear();
        List<Meta> metas = metaRepository.findByAnoOrderByMesAsc(anoAtual);
        model.addAttribute("metas", metas);
        model.addAttribute("anoAtual", anoAtual);
        return "metas";
    }

    @PostMapping("/metas/salvar")
    public String salvarMeta(@RequestParam Integer mes,
                             @RequestParam Integer ano,
                             @RequestParam Integer quantidade) {
        Optional<Meta> metaExistente = metaRepository.findByMesAndAno(mes, ano);

        if (metaExistente.isPresent()) {
            Meta meta = metaExistente.get();
            meta.setQuantidade(quantidade);
            metaRepository.save(meta);
        } else {
            metaRepository.save(new Meta(mes, ano, quantidade));
        }

        return "redirect:/dashboardTelaInicial?sucesso=true";
    }
}
