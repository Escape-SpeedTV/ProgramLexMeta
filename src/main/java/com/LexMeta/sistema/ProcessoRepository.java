package com.LexMeta.sistema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    long countByStatus(String status);
    long countByStatusContaining(String status);

    List<Processo> findAllByStatus(String status);

    Page<Processo> findByStatus(String status, Pageable pageable);

    Page<Processo> findByResponsavel(String responsavel, Pageable pageable);

    Page<Processo> findByStatusAndResponsavel(String status, String responsavel, Pageable pageable);

    Page<Processo> findByNumeroProcessoContainingIgnoreCaseOrClienteContainingIgnoreCase(
            String numeroProcesso, String AssuntoPrincipal, Pageable pageable
    );
}


