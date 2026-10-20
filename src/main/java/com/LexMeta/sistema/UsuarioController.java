package com.LexMeta.sistema;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private boolean temPermissao(Usuario usuarioLogado){
        if(usuarioLogado == null) return false;
        String cargo = usuarioLogado.getCargo();
        return cargo.equals("T.I") || cargo.equals("ADM Geral") || cargo.equals("Administrador");
    }

    @GetMapping("/configuracoes/colaboradores")
    public String listaColaboradores(HttpSession session, Model model){
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if(!temPermissao(usuarioLogado)){
            return "redirect:/dashboardTelaInicial";
        }

        List<Usuario> colaboradores = usuarioRepository.findAll();

        model.addAttribute("colaboradores", colaboradores);
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "colaboradores";
    }

    @PostMapping("/configuracoes/colaboradores/novo")
    public String novoColaborador(Usuario colaborador, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (!temPermissao(usuarioLogado)) {
            return "redirect:/dashboardTelaInicial";
        }

        String cargoNovo = colaborador.getCargo();
        if ((cargoNovo.equals("ADM Geral") || cargoNovo.equals("T.I")) && !usuarioLogado.getCargo().equals("T.I")) {
            return "redirect:/configuracoes/colaboradores?erro=permissao";
        }

        colaborador.setAtivo(true);
        usuarioRepository.save(colaborador);

        return "redirect:/configuracoes/colaboradores?sucesso=true";
    }

    @PostMapping("/configuracoes/colaboradores/alterar-cargo")
    public String alterarCargo(@RequestParam Long id, @RequestParam String cargo, HttpSession session){
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if(!temPermissao(usuarioLogado)){
            return "redirect:/dashboardTelaInicial";
        }


        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario == null){
            return "redirect:/configuracoes/colaboradores?erro=naoEncontrado";
        }

        if (usuario.getCargo().equals("T.I")) {
            return "redirect:/configuracoes/colaboradores?erro=protegido";
        }

        if (usuario.getCargo().equals("ADM Geral") && usuarioLogado.getCargo().equals("ADM Geral")) {
            return "redirect:/configuracoes/colaboradores?erro=protegido";
        }

        if (usuario.getId().equals(usuarioLogado.getId())) {
            return "redirect:/configuracoes/colaboradores?erro=proprioCargo";
        }

        if ((cargo.equals("ADM Geral") || cargo.equals("T.I")) && !usuarioLogado.getCargo().equals("T.I")) {
            return "redirect:/configuracoes/colaboradores?erro=permissao";
        }

        if(usuario.getCargo().equals("T.I")){
            return "redirect:/configuracoes/colaboradores?erro=protegido";
        }

        usuario.setCargo(cargo);
        usuarioRepository.save(usuario);

        return "redirect:/configuracoes/colaboradores?sucesso=true";
    }

    @PostMapping("/configuracoes/colaboradores/toggle-ativo")
    public String toggleAtivo(@RequestParam Long id, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (!temPermissao(usuarioLogado)) {
            return "redirect:/dashboardTelaInicial";
        }

        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/configuracoes/colaboradores?erro=naoEncontrado";
        }

        if (usuario.getId().equals(usuarioLogado.getId())) {
            return "redirect:/configuracoes/colaboradores?erro=proprio";
        }

        if (usuario.getCargo().equals("T.I")) {
            return "redirect:/configuracoes/colaboradores?erro=protegido";
        }

        usuario.setAtivo(!usuario.getAtivo());
        usuarioRepository.save(usuario);

        return "redirect:/configuracoes/colaboradores?sucesso=true";
    }

    @PostMapping("/configuracoes/colaboradores/excluir")
    public String excluirColaborador(@RequestParam Long id, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (!temPermissao(usuarioLogado)) {
            return "redirect:/dashboardTelaInicial";
        }

        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/configuracoes/colaboradores?erro=naoEncontrado";
        }

        if (usuario.getId().equals(usuarioLogado.getId())) {
            return "redirect:/configuracoes/colaboradores?erro=proprio";
        }

        if (usuario.getCargo().equals("T.I")) {
            return "redirect:/configuracoes/colaboradores?erro=protegido";
        }

        usuarioRepository.delete(usuario);

        return "redirect:/configuracoes/colaboradores?sucesso=true";
    }
}
