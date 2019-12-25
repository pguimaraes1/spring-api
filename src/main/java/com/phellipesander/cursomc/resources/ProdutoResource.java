package com.phellipesander.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phellipesander.cursomc.dto.ProdutoDTO;
import com.phellipesander.cursomc.entity.Produto;
import com.phellipesander.cursomc.resources.utils.URL;
import com.phellipesander.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Long id) {
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public List<Produto> findAll(){
		List<Produto> produtos = service.findAll();
		return produtos;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orederBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		List<Long> ids = URL.decodeIntegerList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<Produto> pages = service.search(nomeDecoded, ids, page, linesPerPage, orederBy, direction);
		Page<ProdutoDTO> produtosDto = pages.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(produtosDto);
	}
}
