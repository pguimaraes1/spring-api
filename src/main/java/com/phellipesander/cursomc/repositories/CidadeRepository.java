package com.phellipesander.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phellipesander.cursomc.entity.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

	
}
