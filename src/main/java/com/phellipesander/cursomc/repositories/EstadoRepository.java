package com.phellipesander.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phellipesander.cursomc.entity.Estado;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

    @Transactional(readOnly = true)
    public List<Estado> findAllByOrderByNome();
	
}
