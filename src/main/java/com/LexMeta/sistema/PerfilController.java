package com.LexMeta.sistema;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

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

    @PostMapping("/perfil/alterar-foto")
    public String alterarFoto(@RequestParam("foto")MultipartFile foto, HttpSession session){
        try{
            Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
            if(usuarioLogado == null || foto.isEmpty()){
                return "redirect:/perfil?erro=true";
            }

            String tipoArquivo = foto.getContentType();
            if(tipoArquivo == null || !tipoArquivo.startsWith("image/")){
                return "redirect:/perfil?erro=true";
            }

            if(foto.getSize() > 2 * 1024 * 1024){
                return "redirect:/perfil?erro=true";
            }

            String pastaUpload = System.getProperty("user.dir") + "/uploads/fotos/";
            File pasta = new File(pastaUpload);
            if(!pasta.exists()){
                boolean criada = pasta.mkdirs();
                System.out.println("Pasta criada: " + criada + " em " + pasta.getAbsolutePath());
            }

            String nomeOriginal = foto.getOriginalFilename();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeUnico = "foto_" + usuarioLogado.getId() + "_" + System.currentTimeMillis() + extensao;

            File arquivoDestino = new File(pasta, nomeUnico);
            foto.transferTo(arquivoDestino);

            System.out.println("Arquivo salvo em: " + arquivoDestino.getAbsolutePath());

            Usuario usuario = usuarioRepository.findById(usuarioLogado.getId()).orElse(null);
            if (usuario != null) {
                usuario.setCaminhoFoto("uploads/fotos/" + nomeUnico);
                usuarioRepository.save(usuario);
                session.setAttribute("usuarioLogado", usuario);
            }
            return "redirect:/perfil?sucesso=true";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/perfil?erro=true";
        }
    }
}
