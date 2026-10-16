package com.LexMeta.sistema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {
    Optional<Meta> findByMesAndAno(Integer mes, Integer ano);

    List<Meta> findByAnoOrderByMesAsc(Integer ano);

    List<Meta> findAllByOrderByAnoDescMesDesc();
}
