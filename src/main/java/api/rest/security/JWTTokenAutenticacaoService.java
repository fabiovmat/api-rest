package api.rest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import api.rest.ApplicationContextLoad;
import api.rest.model.Usuario;
import api.rest.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {

	/* tempo de validade do token --milissegundos */
	private static final long EXPIRATION_TIME = 1728000000;

	/* uma senha unica para compor a autenticacao */
	private static final String SECRET = "SenhaExtremamenteSecreta";

	/* Prefixo padrao de token */
	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	/* gerando token de autenticacao e adicionaod ao cabecalho e resposta http */
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {

		/* montagem do token */
		String JWT = Jwts.builder()/* chama o gerador de token */
				.setSubject(username)/* adiciona o usuario */
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))/* tempo de exipiracao */
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();/* compactacao de algoritmos e geracao de senha */

		/* junta o token com o prefixo */
		String token = TOKEN_PREFIX + " " + JWT;/* Bearer 7887897998e789e789 */

		/* adiciona o cabecalho http */
		response.addHeader(HEADER_STRING, token);/* Authorization: Bearer 7887897998e789e789 */
		
		/*Liberando responsta para portas diferentes que usam a API caso clientes web */
		liberacaoCors(response);
		
		/* escreve token como resposta no corpo do http */
		response.getWriter().write("{\"Authorization\": \"" +token+ "\"}");

	}

	/* retorna o usuario validado com token ou caso nao seja valido retorna null */
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

		/* pega o token enviado no cabecalho http */
		String token = request.getHeader(HEADER_STRING);

		try {
		
		
		if (token != null) {
			
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim(); 
			
			System.out.println(tokenLimpo);
			

			/* faz a validacao do token do usuario na requisicao */
			String user = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(tokenLimpo) 
					.getBody()
					.getSubject();

			if (user != null) {

				Usuario usuario = ApplicationContextLoad.getApplicationContext().getBean(UsuarioRepository.class)
						.findUserByLogin(user);

				if (usuario != null) {
					
					System.out.println(usuario.getToken());
					if (tokenLimpo.equalsIgnoreCase(usuario.getToken())) {
						
						
					/* retorna o usuario logado */
					return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(),
							usuario.getAuthorities());

					}
				}

			}

		}
		
		}catch (io.jsonwebtoken.ExpiredJwtException e) {
			try {
				response.getOutputStream().println("Seu token esta expirado, faça o login ou informe um novo TOken para autenticação");
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
		
		liberacaoCors(response);
		return null;/* nao autorizado */
	}

	private void liberacaoCors(HttpServletResponse response) {
		
		if (response.getHeader("Access-Control-Allow-Origin")   == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
		
		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
			
		if (response.getHeader("Access-Controls-Request-Headers") == null) {
			response.addHeader("Access-Controls-Request-Headers", "*");
		}
			
		if (response.getHeader("Access-Controls-Allow-Methods") == null) {
			response.addHeader("Access-Controls-Allow-Methods", "*");
		}	
		
		
		}
		
		
		
		
	}
	
}
