package com.LexMeta.sistema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    long countByStatus(String status);
    long countByStatusContaining(String status);
}
