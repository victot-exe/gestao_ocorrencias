package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.config.security.TokenService;
import com.victot.gestao_ocorrencias.exceptions.NotAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class BaseController {
    protected TokenService tokenService;

    protected String getCpfAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName(); // Por padrão, traz o Principal/Subject (CPF)
        }
        return null;
    }

    protected String getPessoaIdAutenticada() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getCredentials() instanceof String token) {
            return tokenService.getPessoaIdFromToken(token);
        }
        return null;
    }

    protected String getRoleAutenticada() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new NotAuthException("Usuário não está autenticado no contexto de segurança.");
            // Nota: Você pode criar uma exceção específica de Auth aqui, se preferir.
        }

        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new NotAuthException("Houve um problema com a autenticação: Usuário sem perfil de acesso."));
    }
}
