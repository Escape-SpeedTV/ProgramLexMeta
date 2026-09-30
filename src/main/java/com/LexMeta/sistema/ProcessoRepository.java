package com.LexMeta.sistema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    long countByStatus(String status);
    long countByStatusContaining(String status);

    Page<Processo> findByStatus(String status, Pageable pageable);
}


