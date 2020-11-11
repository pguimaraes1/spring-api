package com.phellipesander.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.phellipesander.cursomc.dto.ClienteDTO;
import com.phellipesander.cursomc.dto.ClienteNewDTO;
import com.phellipesander.cursomc.entity.Cidade;
import com.phellipesander.cursomc.entity.Cliente;
import com.phellipesander.cursomc.entity.Endereco;
import com.phellipesander.cursomc.entity.enums.Perfil;
import com.phellipesander.cursomc.entity.enums.TipoCliente;
import com.phellipesander.cursomc.repositories.ClienteRepository;
import com.phellipesander.cursomc.repositories.EnderecoRepository;
import com.phellipesander.cursomc.security.UserSS;
import com.phellipesander.cursomc.services.exception.AuthorizationException;
import com.phellipesander.cursomc.services.exception.DataIntegrityException;
import com.phellipesander.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository endRepo;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	public Cliente find(Long id) {
		
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@GetMapping
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		endRepo.saveAll(obj.getEnderecos());
		obj = clienteRepository.save(obj);
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}

	public void delete(Long id) {
		find(id);
		try {
			clienteRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente que possui pedidos!");
		}
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orederBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orederBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null , null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEmum(objDto.getTipoCliente()), passwordEncoder.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null , null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile multipartFile){
		UserSS user = UserService.authenticated();
		if (user == null){
			throw new AuthorizationException("Acesso negado.");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

//
//		URI uri = s3Service.uploadFile(multipartFile);
//		Cliente cliente = clienteRepository.getOne(user.getId());
//		cliente.setImgUrl(uri.toString());
//		clienteRepository.save(cliente);
//		return uri;
	}

} 
