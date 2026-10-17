package com.LexMeta.sistema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository  extends JpaRepository<Configuracao, Long>{
}
