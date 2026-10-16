package com.LexMeta.sistema;

import jakarta.persistence.*;

@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer mes;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Integer quantidade;

    public Meta(){}

    public Meta(Integer mes, Integer ano, Integer quantidade){
        this.mes = mes;
        this.ano = ano;
        this.quantidade = quantidade;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public Integer getMes(){
        return mes;
    }
    public void setMes(Integer mes){
        this.mes = mes;
    }

    public Integer getAno(){
        return ano;
    }
    public void setAno(Integer ano){
        this.ano = ano;
    }

    public Integer getQuantidade(){
        return quantidade;
    }
    public void setQuantidade(Integer quantidade){
        this.quantidade = quantidade;
    }
}
