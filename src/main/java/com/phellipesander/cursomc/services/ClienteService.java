package com.phellipesander.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.phellipesander.cursomc.entity.Cliente;
import com.phellipesander.cursomc.repositories.ClienteRepository;
import com.phellipesander.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Long id) {
		Optional<Cliente> obj = repo.findById(id);
 		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@GetMapping
	public List<Cliente> listar(){
		List<Cliente> lista = repo.findAll();
		return lista;
	}

}
