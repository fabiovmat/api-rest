package api.rest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import api.rest.model.Usuario;



@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
//no ID precisa passar qual tipo de dado sera o primary key
	
	
	@Query("select u from Usuario u where u.login = ?1")//1parametro no metodo = 1 posicao(login)
	Usuario findUserByLogin(String login);
	
}
