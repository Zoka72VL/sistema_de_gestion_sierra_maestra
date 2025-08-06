package com.sca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sca.model.Madurador;

@Repository
public interface MaduradorRepository  extends JpaRepository <Madurador, Long>{

}
