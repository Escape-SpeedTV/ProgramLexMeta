package com.LexMeta.sistema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.time.LocalDate;
import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @ModelAttribute("mesAtual")
    public Integer getMesAtual() {
        return LocalDate.now().getMonthValue();
    }

    @ModelAttribute("anoAtualModal")
    public Integer getAnoAtual() {
        return LocalDate.now().getYear();
    }

    @ModelAttribute("nomeMesAtual")
    public String getNomeMesAtual() {
        String[] nomesMeses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        return nomesMeses[LocalDate.now().getMonthValue() - 1];
    }

    @ModelAttribute("quantidadeMetaAtual")
    public Integer getQuantidadeMetaAtual() {
        int mesAtual = LocalDate.now().getMonthValue();
        int anoAtual = LocalDate.now().getYear();
        Optional<Meta> metaDoMes = metaRepository.findByMesAndAno(mesAtual, anoAtual);
        return metaDoMes.map(Meta::getQuantidade).orElse(0);

    }

    @ModelAttribute("configGlobal")
    public Configuracao getConfigGlobal(){
        return configuracaoRepository.findAll().stream().findFirst().orElse(new Configuracao());
    }
}
