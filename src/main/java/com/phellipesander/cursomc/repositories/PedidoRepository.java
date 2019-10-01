package com.phellipesander.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phellipesander.cursomc.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	
}
