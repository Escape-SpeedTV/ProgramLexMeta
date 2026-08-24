function toggleSenha(){
    var campoSenha = document.getElementById("senha");
    var iconeOlho = document.getElementById("iconeOlho");

    if (campoSenha.type === "password"){
        campoSenha.type = "text";
        iconeOlho.src = "/imagens/olhoAberto.png";
    }else{
        campoSenha.type = "password";
        iconeOlho.src = "/imagens/olho.png";
    }
}