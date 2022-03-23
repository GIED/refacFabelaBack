package com.refacFabela.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.refacFabela.service.impl.UserDetailsServiceImpl;



public class JwtTokenFilter extends OncePerRequestFilter{
	
	private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
	
	/**
	 * comprueba en cada peticion que el token sea valido y permite el acceso al recurso
	 */
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getToken(req);
			if (token != null && jwtProvider.validateToken(token)) {
				String usuario = jwtProvider.usuarioFromToken(token);
				UserDetails userDetails = userDetailsService.loadUserByUsername(usuario);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				
			}
			
		} catch (Exception e) {
			logger.error("fallo en el método doFilter", e.getMessage());
		}
		
		filterChain.doFilter(req, res);
	
		
	}
	
	private String getToken(HttpServletRequest req) {
		String header = req.getHeader("Authorization");
		
		if (header != null && header.startsWith("Bearer")) {
			return header.replace("Bearer", "");
		}
		
		return null;
		
	}

}
