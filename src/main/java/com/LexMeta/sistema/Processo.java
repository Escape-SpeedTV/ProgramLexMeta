package com.LexMeta.sistema;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "processos")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroProcesso;
    private String cliente;
    private String status;
    private Double valorRecebido;
    private LocalDate dataCadastro;
    private String descricao;
    private String responsavel;

    public Processo(){
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroProcesso() { return numeroProcesso; }
    public void setNumeroProcesso(String numeroProcesso) { this.numeroProcesso = numeroProcesso; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getValorRecebido() { return valorRecebido; }
    public void setValorRecebido(Double valorRecebido) { this.valorRecebido = valorRecebido; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
}
