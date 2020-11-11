package com.phellipesander.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phellipesander.cursomc.entity.Cidade;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

    @Transactional(readOnly = true)
    public List<Cidade> findCidadesByEstadoIdOrderByNome(Long estadoId);
	
}
