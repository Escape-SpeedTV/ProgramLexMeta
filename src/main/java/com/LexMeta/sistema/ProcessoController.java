package com.LexMeta.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProcessoController {

    @Autowired
    private ProcessoRepository processoRepository;

    @GetMapping("/novo-processo")
    public String mostrarFormulario(Model model){
        model.addAttribute("processo", new Processo());
        return "novo-processo";
    }

    @PostMapping("/noco-processo")
    public String salvarProcesso(Processo processo){
        processo.setDataCadastro(java.time.LocalDate.now());

        processoRepository.save(processo);

        return "redirect:/dashboard";
    }
}
