package com.refacFabela.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class ApiKeyMetaCompraFilter extends OncePerRequestFilter{
	
	private final String headerName;
    private final String expectedToken;

    public ApiKeyMetaCompraFilter(String headerName, String expectedToken) {
        this.headerName = headerName;
        this.expectedToken = expectedToken;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        // URI real:      /api/refacFabela/meta-compra/por-no-parte
        // Context-path:  /api/refacFabela
        // Relativo:      /meta-compra/por-no-parte
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        String rel = uri.substring(ctx.length());
        return !rel.startsWith("/meta-compra/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        // 1) Header dedicado (p.ej. X-API-TOKEN)
        String provided = req.getHeader(headerName);

        // 2) Alternativa: Authorization: ApiKey <token>
        if (provided == null || provided.trim().isEmpty()) {
            String auth = req.getHeader("Authorization");
            if (auth != null && auth.startsWith("ApiKey ")) {
                provided = auth.substring("ApiKey ".length()).trim();
            }
        }

        // Si el token coincide, autenticamos con ROLE_META_COMPRA_READ
        if (provided != null && constantTimeEquals(expectedToken, provided)) {
            Authentication auth = new AbstractAuthenticationToken(
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_META_COMPRA_READ"))) {
                @Override public Object getCredentials() { return "N/A"; }
                @Override public Object getPrincipal() { return "api-key-client"; }
                { setAuthenticated(true); }
            };
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Importante: no cortamos la cadena aquí; dejamos que siga.
        chain.doFilter(req, res);
    }

    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        byte[] aa = a.getBytes(StandardCharsets.UTF_8);
        byte[] bb = b.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(aa, bb);
    }

}
