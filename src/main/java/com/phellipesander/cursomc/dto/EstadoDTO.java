package com.phellipesander.cursomc.dto;

import com.phellipesander.cursomc.entity.Estado;

public class EstadoDTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    public EstadoDTO(Estado estado) {
        this.id = estado.getId();
        this.nome = estado.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
