package com.phellipesander.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phellipesander.cursomc.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	
}
