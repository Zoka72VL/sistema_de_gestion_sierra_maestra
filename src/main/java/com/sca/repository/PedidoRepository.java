package com.sca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sca.model.Pedido;

@Repository
public interface PedidoRepository  extends JpaRepository <Pedido, Long>{

}
