package com.LexMeta.sistema;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/perfil")
    public String mostrarPerfil(HttpSession session, Model model){
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if(usuarioLogado == null){
            return "redirect:/";
        }

        Usuario usuario = usuarioRepository.findById(usuarioLogado.getId()).orElse(usuarioLogado);
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    @PostMapping("/perfil/salvar")
    public String salvarPerfil(Usuario usuarioAtualizado, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/";
        }

        Usuario usuario = usuarioRepository.findById(usuarioLogado.getId()).orElse(null);
        if (usuario == null) {
            return "redirect:/";
        }

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setCargo(usuarioAtualizado.getCargo());
        usuario.setAreaAtuacao(usuarioAtualizado.getAreaAtuacao());
        usuario.setTelefone(usuarioAtualizado.getTelefone());

        usuarioRepository.save(usuario);
        session.setAttribute("usuarioLogado", usuario);

        return "redirect:/perfil?sucesso=true";
    }
}
