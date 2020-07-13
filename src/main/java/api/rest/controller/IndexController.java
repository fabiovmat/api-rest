package api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController/*ARQUITETURA REST*/
@RequestMapping(value = "/usuario")
public class IndexController {
	
	
	/*servico RESTFUL*/
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity <String> init(@RequestParam(value = "nome", defaultValue = "Nome não informado")String nome , @RequestParam("salario") Long salario) {
		
		System.out.println("parametro sendo recebido " + nome +""+ salario);
		return new ResponseEntity<String>("Ola usuario REST Spring Boot, seu nome é: " + nome + " e o seu salario é " + salario, HttpStatus.OK);
		
	}
	
}
