package com.LexMeta.sistema;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocumentosController {

    @GetMapping("/documentos")
    public String mostrarDocumentos(){
        return "documentos";
    }
}
