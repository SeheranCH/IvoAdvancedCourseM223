package ch.course223.advanced.security;

import ch.course223.advanced.domainmodels.user.UserService;
import ch.course223.advanced.domainmodels.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private UserService userService;
	
	private BCryptPasswordEncoder pwEncoder;
	
	private PropertyReader propertyReader;

	private UserMapper userMapper;

	@Autowired
	public SecurityConfiguration(
			UserService userService,
			BCryptPasswordEncoder pwEncoder,
			UserMapper userMapper
	) {
		super();
		this.userService = userService;
		this.pwEncoder = pwEncoder;
		this.userMapper = userMapper;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(pwEncoder);
	}

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		//configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080","http://localhost:8084")); Will be used at a later stage to only allow NOA Frontend to access our API
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		propertyReader = new PropertyReader("jwt.properties");

		http.headers().frameOptions().sameOrigin();

		http.cors().and().csrf().disable().
				authorizeRequests()
				.antMatchers("/welcome", "/login", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html",
						"/webjars/**", "/swagger.yaml", "/**")
				.permitAll()
				.anyRequest().authenticated().and()
				.addFilterAfter(
						new JWTAuthenticationFilter(
								new AntPathRequestMatcher("/login", "POST"),
								authenticationManagerBean(),
								propertyReader,
								userMapper)
						, UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(
						new JWTAuthorizationFilter(userService, propertyReader), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
}
