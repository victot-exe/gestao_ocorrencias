package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.config.security.TokenService;
import com.victot.gestao_ocorrencias.dtos.auth.AuthRequest;
import com.victot.gestao_ocorrencias.dtos.auth.TokenResponse;
import com.victot.gestao_ocorrencias.entity.Pessoa;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager maneger;
    private final TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthRequest request){
        var authToken = new UsernamePasswordAuthenticationToken(request.cpf(), request.password());
        var auth = maneger.authenticate(authToken);

        var tokenJWT = tokenService.generateToken((Pessoa) Objects.requireNonNull(auth.getPrincipal()));

        return ResponseEntity.ok(new TokenResponse(tokenJWT));
    }
}
