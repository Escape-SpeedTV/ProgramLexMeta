package com.LexMeta.sistema;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginController {

    @GetMapping("/")

    public String mostrarTelaLogin(@RequestParam(value = "erro", required = false) String erro, Model model){
        if (erro != null){
            model.addAttribute("mensagemErro", "E-mail ou senha incorretos.");
        }
        return "login";
    }

    @PostMapping("/login")

    public String fazerLogin(Usuario usuario){
        if("admin@a.com".equals(usuario.getEmail()) && "123".equals(usuario.getSenha())){
            return "redirect:/dashboardTelaInicial";
        } else{
            return "redirect:/?erro=true";
        }
    }
}


