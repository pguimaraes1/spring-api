package com.phellipesander.cursomc.resources;

import com.phellipesander.cursomc.dto.CidadeDTO;
import com.phellipesander.cursomc.dto.EstadoDTO;
import com.phellipesander.cursomc.entity.Cidade;
import com.phellipesander.cursomc.entity.Estado;
import com.phellipesander.cursomc.services.CidadeService;
import com.phellipesander.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadosResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll(){
        List<Estado> list = estadoService.findAll();
        List<EstadoDTO> dtoList = list.stream().map(EstadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping(value = "/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Long estadoId){
        List<Cidade> list = cidadeService.findCidadesByEstadoIdOrderByNome(estadoId);
        List<CidadeDTO> dtoList = list.stream().map(CidadeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(dtoList);
     }
}
