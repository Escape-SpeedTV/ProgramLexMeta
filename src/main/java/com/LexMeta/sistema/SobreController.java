package com.LexMeta.sistema;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SobreController {
    @GetMapping("/configuracoes/sobre")
    public String mostrarSenha(){
        return "sobre";
    }
}
