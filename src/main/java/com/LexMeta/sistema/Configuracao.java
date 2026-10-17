package com.LexMeta.sistema;

import jakarta.persistence.*;

@Entity
@Table(name = "configuracoes")
public class Configuracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String nome;
    private String razaoSocial;

    @Column(length = 500)
    private String descricao;
    private String telefone1;
    private String telefone2;
    private String cnpj;
    private String email;
    private String cep;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;
    private String instagram;

    public Configuracao(){}

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

    public String getRazaoSocial(){
        return razaoSocial;
    }
    public void setRazaoSocial(String razaoSocial){
        this.razaoSocial = razaoSocial;
    }

    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getTelefone1(){
        return telefone1;
    }
    public void setTelefone1(String telefone1){
        this.telefone1 = telefone1;
    }

    public String getTelefone2(){
        return telefone2;
    }
    public void setTelefone2(String telefone2){
        this.telefone2 = telefone2;
    }

    public String getCnpj(){
        return cnpj;
    }
    public void setCnpj(String cnpj){
        this.cnpj = cnpj;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getCep(){
        return cep;
    }
    public void setCep(String cep){
        this.cep = cep;
    }

    public String getEndereco(){
        return endereco;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public String getNumero(){
        return numero;
    }
    public void setNumero(String numero){
        this.numero = numero;
    }

    public String getBairro() {return bairro;
    }
    public void setBairro(String bairro){
        this.bairro = bairro;
    }

    public String getCidade(){
        return cidade;
    }
    public void setCidade(String cidade){
        this.cidade = cidade;
    }

    public String getUf(){
        return uf;
    }
    public void setUf(String uf){
        this.uf = uf;
    }

    public String getComplemento(){
        return complemento;
    }
    public void setComplemento(String complemento){
        this.complemento = complemento;
    }

    public String getInstagram(){
        return instagram;
    }
    public void setInstagram(String instagram){
        this.instagram = instagram;
    }
}
