package com.phellipesander.cursomc.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phellipesander.cursomc.entity.Cliente;
import com.phellipesander.cursomc.entity.ItemPedido;
import com.phellipesander.cursomc.entity.PagamentoComBoleto;
import com.phellipesander.cursomc.entity.Pedido;
import com.phellipesander.cursomc.entity.enums.EstadoPagamento;
import com.phellipesander.cursomc.repositories.ItemPedidoRepository;
import com.phellipesander.cursomc.repositories.PagamentoRepository;
import com.phellipesander.cursomc.repositories.PedidoRepository;
import com.phellipesander.cursomc.security.UserSS;
import com.phellipesander.cursomc.services.exception.AuthorizationException;
import com.phellipesander.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Long id) {
		
		Optional<Pedido> obj = pedidoRepository.findById(id);
 		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	public List<Pedido> findAll(){
		return pedidoRepository.findAll();
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = pedidoRepository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(new BigDecimal(0));
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
		
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orederBy, String direction ){
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orederBy);
		Cliente cliente = clienteService.find(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
	
}
