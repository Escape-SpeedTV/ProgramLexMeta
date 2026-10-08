package com.LexMeta.sistema;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "processos")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroProcesso;
    private String numeroProcessoLivre;
    private String cliente;
    private String status;
    private String prioridade;
    private String responsavel;
    private String varaOrgao;
    private LocalDate dataDistribuicao;
    private String valorRecebido;
    private LocalDate dataCadastro;

    @Column(length = 2000)
    private String descricao;

    @OneToMany(mappedBy = "processo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos = new ArrayList<>();


    public Processo(){
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getNumeroProcesso(){
        return numeroProcesso;
    }
    public void setNumeroProcesso(String numeroProcesso){
        this.numeroProcesso = numeroProcesso;
    }

    public String getNumeroProcessoLivre(){
        return numeroProcessoLivre;
    }
    public void setNumeroProcessoLivre(String numeroProcessoLivre){
        this.numeroProcessoLivre = numeroProcessoLivre;
    }

    public String getCliente(){
        return cliente;
    }
    public void setCliente(String cliente){
        this.cliente = cliente;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getPrioridade(){
        return prioridade;
    }
    public void setPrioridade(String prioridade){
        this.prioridade = prioridade;
    }

    public String getResponsavel(){
        return responsavel;
    }
    public void setResponsavel(String responsavel){
        this.responsavel = responsavel;
    }

    public String getVaraOrgao(){
        return varaOrgao;
    }
    public void setVaraOrgao(String varaOrgao){
        this.varaOrgao = varaOrgao;}

    public LocalDate getDataDistribuicao(){
        return dataDistribuicao;
    }
    public void setDataDistribuicao(LocalDate dataDistribuicao){
        this.dataDistribuicao = dataDistribuicao;
    }

    public String getValorRecebido(){
        return valorRecebido;
    }
    public void setValorRecebido(String valorRecebido){
        this.valorRecebido = valorRecebido;
    }

    public LocalDate getDataCadastro(){
        return dataCadastro;
    }
    public void setDataCadastro(LocalDate dataCadastro){
        this.dataCadastro = dataCadastro;
    }

    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }
}
