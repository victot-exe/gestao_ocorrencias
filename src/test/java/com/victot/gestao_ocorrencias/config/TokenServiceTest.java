package com.victot.gestao_ocorrencias.config;

import com.victot.gestao_ocorrencias.BaseIntegrationTest;
import com.victot.gestao_ocorrencias.entity.Pessoa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Deve gerar token JWT válido com subject e claims corretos")
    void deveGerarEValidarToken() {
        Pessoa operador = pessoaRepository.findById(OPERADOR_ID).orElseThrow();

        String token = tokenService.generateToken(operador);

        assertNotNull(token);
        assertFalse(token.isBlank());

        String subjectCpf = tokenService.validateTokenAndObterSubject(token);
        assertEquals(OPERADOR_CPF, subjectCpf);

        String extractedId = tokenService.getPessoaIdFromToken(token);
        assertEquals(OPERADOR_ID, extractedId);
    }

    @Test
    @DisplayName("Deve retornar null ao validar token inválido ou corrompido")
    void deveRetornarNullParaTokenInvalido() {
        String tokenInvalido = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.token_falso_invalido";

        String subject = tokenService.validateTokenAndObterSubject(tokenInvalido);

        assertNull(subject);
    }
}
