package com.LexMeta.sistema;

public class usuario {
    private String email;
    private String senha;

    public usuario() {}

    public String getEmail(){
        return email;
    }

    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
}
