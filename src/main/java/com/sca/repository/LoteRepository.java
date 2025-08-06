package com.sca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sca.model.Lote;

@Repository
public interface LoteRepository  extends JpaRepository <Lote, Long>{

}
