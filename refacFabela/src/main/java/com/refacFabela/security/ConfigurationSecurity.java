package com.refacFabela.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.refacFabela.jwt.JwtEntryPoint;
import com.refacFabela.jwt.JwtTokenFilter;
import com.refacFabela.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtEntryPoint jwtEntryPoint;

	@Value("${security.api.meta-compra.header-name:X-API-TOKEN}")
	private String metaHeader;

	@Value("${security.api.meta-compra.token:change-me}")
	private String metaToken;
	
	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}

	@Bean
	public ApiKeyMetaCompraFilter apiKeyMetaCompraFilter() {
		return new ApiKeyMetaCompraFilter(metaHeader, metaToken);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.authorizeRequests()
				.antMatchers("/auth/**", "/verComprobante/**").permitAll()

				// 🔐 Esta ruta exige el rol concedido por el API Key filter
				.antMatchers("/meta-compra/**").hasRole("META_COMPRA_READ")

				// El resto funciona con tu JWT normal
				.anyRequest().authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Orden: primero API Key (solo aplicará a /api/meta-compra/**), después JWT
		// IMPORTANTE: usa AnonymousAuthenticationFilter como ancla
		http.addFilterBefore(apiKeyMetaCompraFilter(),
		    org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);

		http.addFilterBefore(jwtTokenFilter(),
		    org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);
	}
}
