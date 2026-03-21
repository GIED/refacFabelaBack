package com.refacFabela.jwt;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.refacFabela.dto.JwtDto;
import com.refacFabela.model.UsuarioPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	/**
	 * genera token, valida y comprueba que no haya expirado
	 */

	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private int expiration;

	public String generateToken(Authentication authentication) {
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
		List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		return Jwts.builder()
				.setSubject(usuarioPrincipal.getUsername())
				.claim("nId", usuarioPrincipal.getnId())
				.claim("nombreUsuario", usuarioPrincipal.getsNombreUsuario())
				.claim("roles", roles)
				.claim("nIdCliente", usuarioPrincipal.getnIdCliente())
				.claim("nTipoRevendedor", usuarioPrincipal.getnTipoRevendedor())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000L))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public String usuarioFromToken(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
			return true;

		} catch (MalformedJwtException e) {
			logger.error("Token mal formado: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("Token no soportado: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("Token expirado: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Token vacío: {}", e.getMessage());
		} catch (SignatureException e) {
			logger.error("Fallo en la firma: {}", e.getMessage());
		}
		return false;
	}
	
	public String refreshToken(JwtDto jwtDto) throws ParseException {
		try {
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(jwtDto.getToken());
			// Token aún válido — retornarlo tal cual (no generar null)
			return jwtDto.getToken();
		} catch (ExpiredJwtException e) {
				logger.error("Refresh de token expirado, generando nuevo token");
				JWT jwt = JWTParser.parse(jwtDto.getToken());
				JWTClaimsSet claims = jwt.getJWTClaimsSet();
				String usuario = claims.getSubject();
				Long nId = (Long) claims.getClaim("nId");
				String nombreUsuario = (String) claims.getClaim("nombreUsuario");
				List<String> roles = (List<String>) claims.getClaim("roles");
				Long nIdCliente = (Long) claims.getClaim("nIdCliente");
				Long nTipoRevendedor = (Long) claims.getClaim("nTipoRevendedor");
				
				return Jwts.builder()
						.setSubject(usuario)
						.claim("nId", nId)
						.claim("nombreUsuario", nombreUsuario)
						.claim("roles", roles)
						.claim("nIdCliente", nIdCliente)
						.claim("nTipoRevendedor", nTipoRevendedor)
						.setIssuedAt(new Date())
						.setExpiration(new Date(new Date().getTime() + expiration * 1000L))
						.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
		}
	}

}
