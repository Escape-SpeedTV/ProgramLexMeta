package com.LexMeta.sistema;

import jakarta.persistence.*;

@Entity
@Table(name = "documentos")

public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeArquivo;
    private String caminhoArquivo;
    private String tipo;
    private String tamanho;
    private String dataEnvio;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;

    public Documento(){
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getNomeArquivo(){
        return nomeArquivo;
    }
    public void setNomeArquivo(String nomeArquivo){
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminhoArquivo(){
        return caminhoArquivo;
    }
    public void setCaminhoArquivo(String caminhoArquivo){
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getTipo(){
        return tipo;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getTamanho(){
        return tamanho;
    }
    public void setTamanho(String tamanho){
        this.tamanho = tamanho;
    }

    public String getDataEnvio(){
        return dataEnvio;
    }
    public void setDataEnvio(String dataEnvio){
        this.dataEnvio = dataEnvio;
    }

    public Processo getProcesso(){
        return processo;
    }
    public void setProcesso(Processo processo){
        this.processo = processo;
    }

}
