package com.LexMeta.sistema;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/")
    //Nesse trecho do código, o que está acontecendo, o RequestParam, ele está pegando o valor de erro, e o que significa? Se o usuário digitar a senha ou email inválido, vai aparecer uma mensagem na tela.
    public String mostrarTelaLogin(@RequestParam(value = "erro", required = false) String erro, Model model){ //O required eu defini que o valor vai ser false, se eu colocar true, o site vai aparecer na tela que a senha ou email estão incorretos, e não queremos isso.
        if (erro != null){
            model.addAttribute("mensagemErro", "E-mail ou senha incorretos.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(Usuario usuarioDigitado, HttpSession session) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(usuarioDigitado.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getSenha().equals(usuarioDigitado.getSenha())) {

                if (usuario.getAtivo() == null || !usuario.getAtivo()) {
                    return "redirect:/?erro=inativo";
                }

                session.setAttribute("usuarioLogado", usuario);
                return "redirect:/dashboardTelaInicial";
            }
        }

        // Login falhou
        return "redirect:/?erro=true";
    }

    @GetMapping("/logout")
    public String fazerLogout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}


