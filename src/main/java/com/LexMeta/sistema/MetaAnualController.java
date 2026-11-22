package com.LexMeta.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class MetaAnualController {

    @Autowired
    private MetaAnualRepository metaAnualRepository;

    @PostMapping("/meta-anual/salvar")
    public String salvarMetaAnual(@RequestParam Integer ano, @RequestParam Integer quantidade) {
        Optional<MetaAnual> metaExistente = metaAnualRepository.findByAno(ano);

        if (metaExistente.isPresent()) {
            MetaAnual meta = metaExistente.get();
            meta.setQuantidade(quantidade);
            metaAnualRepository.save(meta);
        } else {
            MetaAnual nova = new MetaAnual();
            nova.setAno(ano);
            nova.setQuantidade(quantidade);
            nova.setDataCriacao(LocalDate.now());
            metaAnualRepository.save(nova);
        }

        return "redirect:/dashboardTelaInicial?sucesso=true";
    }
}