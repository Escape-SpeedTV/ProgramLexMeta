package com.LexMeta.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

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

        return "redirect:/configuracoes?sucesso=true";
    }

    @PostMapping("/configuracoes/alterar-logo")
    public String alterarLogo(@RequestParam("logo") MultipartFile logo){
        try{
            if(logo.isEmpty()){
                return "redirect:/configuracoes?erro=true";
            }

            String tipo = logo.getContentType();
            if (tipo == null || !tipo.equals("image/png")) {
                return "redirect:/configuracoes?erroTipo=true";
            }

            if (logo.getSize() > 2 * 1024 * 1024){
                return "redirect:/configuracoes?erro=true";
            }

            String pastaUpload = System.getProperty("user.dir") + "/uploads/logos/";
            File pasta = new File(pastaUpload);
            if(!pasta.exists()) pasta.mkdirs();

            String nomeOriginal = logo.getOriginalFilename();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeUnico = "logo_" + System.currentTimeMillis() + extensao;

            File arquivoDestino = new File(pasta, nomeUnico);
            logo.transferTo(arquivoDestino);

            Optional<Configuracao> existente = configuracaoRepository.findAll().stream().findFirst();

            if(existente.isPresent()){
                Configuracao config = existente.get();
                config.setCaminhoLogo("uploads/logos/" + nomeUnico);
                configuracaoRepository.save(config);
            }else{
                Configuracao nova = new Configuracao();
                nova.setCaminhoLogo("uploads/logos/" + nomeUnico);
                configuracaoRepository.save(nova);
            }
            return "redirect:/configuracoes?sucesso=true";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/configuracoes?erro=true";
        }
    }
}
