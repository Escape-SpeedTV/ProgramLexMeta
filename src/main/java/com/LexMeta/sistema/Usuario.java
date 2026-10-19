package com.LexMeta.sistema;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String cargo;

    private String areaAtuacao;
    private String telefone;
    private String caminhoFoto;
    private Boolean ativo = true;
    private String oab;

    public Usuario(){};

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getSenha(){
        return senha;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }

    public String getCargo(){
        return cargo;
    }
    public void setCargo(String cargo){
        this.cargo = cargo;
    }

    public String getAreaAtuacao(){
        return areaAtuacao;
    }
    public void setAreaAtuacao(String areaAtuacao){
        this.areaAtuacao = areaAtuacao;
    }

    public String getTelefone(){
        return telefone;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getCaminhoFoto(){
        return caminhoFoto;
    }
    public void setCaminhoFoto(String caminhoFoto){
        this.caminhoFoto = caminhoFoto;
    }

    public Boolean getAtivo(){
        return ativo;
    }
    public void setAtivo(Boolean ativo){
        this.ativo = ativo;
    }

    public String getOab(){
        return oab;
    }
    public void setOab(String oab){
        this.oab = oab;
    }


}
