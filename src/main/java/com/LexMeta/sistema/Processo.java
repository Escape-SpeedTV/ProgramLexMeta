package com.LexMeta.sistema;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "processos")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Colomn(nullable = false)
    private String numeroProcesso;
    private String cliente;
    private String status;
    private Double valorRecebido;
    private LocalDate dataCadastro;

    public Processo(){
    }

    public Long getId(){
        return id;
    }

    public String getNumeroProcesso(){
        return numeroProcesso;
    }
    public String getCliente(){
        return cliente;
    }
    public String getStatus(){
        return status;
    }
    public Double getValorRecebido(){
        return valorRecebido;
    }
}
