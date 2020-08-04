package api.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import api.rest.service.ImplementacaoUserDetailsService;

/*mapeia URL, enderecos, autoriza ou bloqueia acessos a URL*/
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	
	/*Configura as solicaitacoes de acesso por Http*/
	@Override
		protected void configure(HttpSecurity http) throws Exception {
			
		
		/*Ativando a protecao contra usuarios que nao estao validados por token*/
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		/*Ativando a permissao para acesso a pagina inicial do sistema EX: sistema.com.br/index*/
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		.antMatchers(HttpMethod.OPTIONS,"//**").permitAll()
		
		/*URL de logout - redireciona apos o user fazer logout do sistema*/
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		/*mapeia url de logou e invalida o usuário*/
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
	
		/*Filtra as requisicoes de login para autenticacao*/
	.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
			UsernamePasswordAuthenticationFilter.class)
		
		
		
		/*Filtra demais requisicoes para verificar presenca do TOKEN GWT no READER HTTP */
		.addFilterBefore(new JWTApiAutenticacaoFilter(),UsernamePasswordAuthenticationFilter.class);
	
	
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/*service que ira consultar o usuario no banco de dadoos*/
		auth.userDetailsService(implementacaoUserDetailsService)
		
		/*padrao de codificao de senha*/
		.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	
	
	
	
	
}
