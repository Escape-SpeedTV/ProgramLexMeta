package com.LexMeta.sistema;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name= "contratos")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String nacionalidade;
    private String profissao;
    private String estadoCivil;
    private String cpf;
    private String rg;
    private String orgaoEmissor;
    private String endereco;
    private String cep;

    private String numeroContrato;
    private String numeroTermoReserva;
    private String enderecoImovel;

    private String local;
    private LocalDate dataContrato;
    private LocalDateTime dataHoraGeracao;

    public Contrato(){}

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}

    public String getNacionalidade(){return nacionalidade;}
    public void setNacionalidade(String nacionalidade){this.nacionalidade = nacionalidade;}

    public String getProfissao(){return profissao;}
    public void setProfissao(String profissao){this.profissao = profissao;}

    public String getEstadoCivil(){return estadoCivil;}
    public void setEstadoCivil(String estadoCivil){this.estadoCivil = estadoCivil;}

    public String getCpf(){return cpf;}
    public void setCpf(String cpf){this.cpf = cpf;}

    public String getRg(){return rg;}
    public void setRg(String rg){this.rg = rg;}

    public String getOrgaoEmissor(){return orgaoEmissor;}
    public void setOrgaoEmissor(String orgaoEmissor){this.orgaoEmissor = orgaoEmissor;}

    public String getEndereco(){return endereco;}
    public void setEndereco(String endereco){this.endereco = endereco;}

    public String getCep(){return cep;}
    public void setCep(String cep){this.cep = cep;}

    public String getNumeroContrato(){return numeroContrato;}
    public void setNumeroContrato(String numeroContrato){this.numeroContrato = numeroContrato;}

    public String getNumeroTermoReserva() { return numeroTermoReserva; }
    public void setNumeroTermoReserva(String numeroTermoReserva) { this.numeroTermoReserva = numeroTermoReserva; }

    public String getEnderecoImovel() { return enderecoImovel; }
    public void setEnderecoImovel(String enderecoImovel) { this.enderecoImovel = enderecoImovel; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public LocalDate getDataContrato() { return dataContrato; }
    public void setDataContrato(LocalDate dataContrato) { this.dataContrato = dataContrato; }

    public LocalDateTime getDataHoraGeracao() { return dataHoraGeracao; }
    public void setDataHoraGeracao(LocalDateTime dataHoraGeracao) { this.dataHoraGeracao = dataHoraGeracao; }

}
