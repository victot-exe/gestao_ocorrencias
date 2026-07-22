package com.victot.gestao_ocorrencias.config.security;

import com.victot.gestao_ocorrencias.entity.Pessoa;
import com.victot.gestao_ocorrencias.repository.PessoaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final PessoaRepository pessoaRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recoveryToken(request);

        if(tokenJWT != null) {
            var cpf = tokenService.validateTokenAndObterSubject(tokenJWT);

            if (cpf != null) {
                var pessoa = pessoaRepository.findByCpf(cpf).orElse(null);

                if (pessoa != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(pessoa, null, pessoa.getAuthorities());//TODO verificar como vou escolher a autoridade e tal, com,o isso vai funcionar
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    //TODO ver como fazer por cookie ao inves de Bearer token
    private String recoveryToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "").trim();
        }
        return null;
    }
}
