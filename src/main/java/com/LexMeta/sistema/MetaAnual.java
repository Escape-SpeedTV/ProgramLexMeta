package com.LexMeta.sistema;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "meta_anual")
public class MetaAnual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer ano; // Ex: 2026

    @Column(nullable = false)
    private Integer quantidade; // Ex: 100

    private LocalDate dataCriacao;

    public MetaAnual() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }
}