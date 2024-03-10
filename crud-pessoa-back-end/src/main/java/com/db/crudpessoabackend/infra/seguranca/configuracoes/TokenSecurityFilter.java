package com.db.crudpessoabackend.infra.seguranca.configuracoes;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.db.crudpessoabackend.infra.seguranca.servicos.PessoaUserDetailsService;
import com.db.crudpessoabackend.infra.seguranca.servicos.TokenService;
import com.db.crudpessoabackend.infra.seguranca.utils.TokenUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TokenSecurityFilter extends OncePerRequestFilter {
    
    private TokenService tokenService;

    private PessoaUserDetailsService pessoaUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    
        String authorizationHeader = request.getHeader("Authorization");

        String token = TokenUtils.extractToken(authorizationHeader);

        if (tokenService.isTokenValido(token)){
            String subject = tokenService.getSubject(token);

            UserDetails userDetails = pessoaUserDetailsService.loadUserByUsername(subject);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }


}
