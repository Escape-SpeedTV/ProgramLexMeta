package com.LexMeta.sistema;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/configuracoes/colaboradores")
    public String listaColaboradores(HttpSession session, Model model){
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if(usuarioLogado == null){
            return "redirect:/";
        }

        String cargo = usuarioLogado.getCargo();
        if(!cargo.equals("T.I") && !cargo.equals("ADM Geral") && !cargo.equals("Administrador")){
            return "redirect:/dashboardTelaInicial";
        }

        List<Usuario> colaboradores = usuarioRepository.findAll();

        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "colaboradores";
    }
}
