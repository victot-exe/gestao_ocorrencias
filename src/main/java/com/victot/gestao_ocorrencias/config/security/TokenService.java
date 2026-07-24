package com.victot.gestao_ocorrencias.config.security;

import com.victot.gestao_ocorrencias.entity.Pessoa;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret:minha-chave-secreta-e-muito-longa-123456}")
    private String secret;

    public String generateToken(Pessoa pessoa){
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + 24*60*60*1000);

        return Jwts.builder()
                .issuer("Gestao Ocorrencias API")
                .subject(pessoa.getCpf())
                .claim("id", pessoa.getId())
                .claim("role", pessoa.getPerfil().name())
                .issuedAt(today)
                .expiration(expirationDate)
                .signWith(getChaveAssinatura())
                .compact();
    }

    public String validateTokenAndObterSubject(String tokenJWT) {
        try {
            return Jwts.parser()
                    .verifyWith(getChaveAssinatura())
                    .build()
                    .parseSignedClaims(tokenJWT)
                    .getPayload()
                    .getSubject(); // Retorna o CPF contido no token se for válido
        } catch (Exception e) {
            return null;
        }
    }

    public String getPessoaIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getChaveAssinatura())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", String.class);
    }

    private SecretKey getChaveAssinatura() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
