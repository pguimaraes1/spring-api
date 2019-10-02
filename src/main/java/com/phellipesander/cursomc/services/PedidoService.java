package com.phellipesander.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phellipesander.cursomc.entity.Pedido;
import com.phellipesander.cursomc.repositories.PedidoRepository;
import com.phellipesander.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Long id) {
		Optional<Pedido> obj = repo.findById(id);
 		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public List<Pedido> listar(){
		return repo.findAll();
	}
	
}
