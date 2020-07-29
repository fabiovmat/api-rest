package api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.rest.model.Usuario;
import api.rest.repository.UsuarioRepository;


//@CrossOrigin(origins = "http://localhost:8080")
@CrossOrigin(origins = "*")
@RestController/*ARQUITETURA REST*/
@RequestMapping(value = "/usuario")
public class IndexController {
	
	
	@Autowired /*se fosse CDI sera @Inject*/
	private UsuarioRepository usuarioRepository;
	
	
	
	/*servico RESTFUL*/
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> buscarporId(@PathVariable(value = "id")Long id){
	
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
		
	}
	
	
	//se fosse uma venda por exemplo passaria /{id}/venda
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public String delete (@PathVariable("id")Long id) {
		
		usuarioRepository.deleteById(id);
		
		return "Deletado!";
		
	}
	
	
	/*outro exemplo
	@GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/json")
	public ResponseEntity<Usuario> venda(@PathVariable(value = "id")Long id,
			@PathVariable(value = "venda")Long venda
			){
	
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
		
	}*/
	
	
	
	/*servico de relatorio do funcionario
	@GetMapping(value = "/{id}/relatoriodpdf", produces = "application/pdf")
	public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id")Long id){
	
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		/*o retorno seria um relatorio ----implementar
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
		
	}*/
	
	
	
		
	@GetMapping(value = "/", produces = "application/json")	
	public ResponseEntity<List<Usuario>> usuario(){
		
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		
		return new ResponseEntity<>(list, HttpStatus.OK);
					
	}
		
		@PostMapping(value = "/", produces = "application/json")
		public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){
			
			
			for(int pos = 0; pos < usuario.getTelefones().size(); pos ++) {
				usuario.getTelefones().get(pos).setUsuario(usuario);
			}
			
			
			Usuario usuarioSalvo = usuarioRepository.save(usuario);
			
			return new ResponseEntity<Usuario>(usuarioSalvo,HttpStatus.OK);
			
			
		}
		
		
		@PutMapping(value = "/", produces = "application/json")
		public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){
			
			for(int pos = 0; pos < usuario.getTelefones().size(); pos ++) {
				usuario.getTelefones().get(pos).setUsuario(usuario);
			}
			
			/*implementar outras rotinas antes de atualizar*/
			
			Usuario usuarioSalvo = usuarioRepository.save(usuario);
			
			return new ResponseEntity<Usuario>(usuarioSalvo,HttpStatus.OK);
			
			
		}
		
		
		
		
		
		@PostMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
		public ResponseEntity<Usuario> cadastrarvenda(@PathVariable Long iduser, @PathVariable Long idvenda){
			
			/*aqui seria um processo de venda por ex*/
			//Usuario usuarioSalvo = usuarioRepository.save(usuario);
			
			
			
			return new ResponseEntity<Usuario>(HttpStatus.OK);
			
			
		}
		
		
		/*
		Usuario usuario = new Usuario();
		usuario.setId(50L);
		usuario.setLogin("fabiomatt@gmail.com");
		usuario.setNome("Fabio Verissimo");
		usuario.setSenha("123");
		
		Usuario usuario2= new Usuario();
		usuario2.setId(7L);
		usuario2.setLogin("xxxxxxx@gmail.com");
		usuario2.setNome("XXXXX");
		usuario2.setSenha("222");
		
				
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuario);
		
		usuarios.add(usuario2);
		
		*/
		
		
		
	}
	

		
		
		
	
	

