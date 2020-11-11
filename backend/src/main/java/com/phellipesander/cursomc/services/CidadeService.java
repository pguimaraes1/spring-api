package com.phellipesander.cursomc.services;

import com.phellipesander.cursomc.entity.Cidade;
import com.phellipesander.cursomc.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> findCidadesByEstadoIdOrderByNome(Long estadoId){
        return cidadeRepository.findCidadesByEstadoIdOrderByNome(estadoId);
    }
}
