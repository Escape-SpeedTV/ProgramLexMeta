package com.LexMeta.sistema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaAnualRepository extends JpaRepository<MetaAnual, Long> {
    Optional<MetaAnual> findByAno(Integer ano);
}