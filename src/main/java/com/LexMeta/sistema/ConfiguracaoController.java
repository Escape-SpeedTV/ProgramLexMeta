package com.LexMeta.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;

@Controller
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @GetMapping("/configuracoes")
    public String mostrarConfiguracoes(Model model){
        Configuracao config = configuracaoRepository.findAll().stream().findFirst().orElse(new Configuracao());

        model.addAttribute("config", config);
        return "configuracoes";
    }

    @PostMapping("/configuracoes/salvar")
    public String salvarConfiguracoes(Configuracao configAtualizada){
        Optional<Configuracao> existente = configuracaoRepository.findAll().stream().findFirst();

        if(existente.isPresent()){
            Configuracao config = existente.get();

            config.setNome(configAtualizada.getNome());
            config.setRazaoSocial(configAtualizada.getRazaoSocial());
            config.setDescricao(configAtualizada.getDescricao());
            config.setTelefone1(configAtualizada.getTelefone1());
            config.setTelefone2(configAtualizada.getTelefone2());
            config.setCnpj(configAtualizada.getCnpj());
            config.setEmail(configAtualizada.getEmail());
            config.setCep(configAtualizada.getCep());
            config.setEndereco(configAtualizada.getEndereco());
            config.setNumero(configAtualizada.getNumero());
            config.setBairro(configAtualizada.getBairro());
            config.setCidade(configAtualizada.getCidade());
            config.setUf(configAtualizada.getUf());
            config.setComplemento(configAtualizada.getComplemento());
            config.setInstagram(configAtualizada.getInstagram());
            configuracaoRepository.save(config);
        }else{
            configuracaoRepository.save(configAtualizada);
        }

        return "redirect:/configuracaoes?sucesso=true";
    }
}
