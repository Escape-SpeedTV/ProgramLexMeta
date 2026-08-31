package com.LexMeta.sistema;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginController {

    @GetMapping("/")

    //Nesse trecho do código, o que está acontecendo, o RequestParam, ele está pegando o valor de erro, e o que significa? Se o usuário digitar a senha ou email inválido, vai aparecer uma mensagem na tela.
    public String mostrarTelaLogin(@RequestParam(value = "erro", required = false) String erro, Model model){ //O required eu defini que o valor vai ser false, se eu colocar true, o site vai aparecer na tela que a senha ou email estão incorretos, e não queremos isso.
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


