package com.sca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sca.model.Barril;

@Repository
public interface BarrilRepository  extends JpaRepository <Barril, Long>{

}
