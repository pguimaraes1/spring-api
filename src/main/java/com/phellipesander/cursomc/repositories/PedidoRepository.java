package com.phellipesander.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.phellipesander.cursomc.entity.Cliente;
import com.phellipesander.cursomc.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
}
