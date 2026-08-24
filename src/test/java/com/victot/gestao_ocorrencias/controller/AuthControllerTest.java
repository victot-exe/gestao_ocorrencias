package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.BaseIntegrationTest;
import com.victot.gestao_ocorrencias.dtos.auth.AuthRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Deve autenticar com sucesso para usuário válido e retornar token JWT")
    void deveAutenticarComSucesso() throws Exception {
        AuthRequest request = new AuthRequest(ADMIN_CPF, "123456");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.type", org.hamcrest.Matchers.is("Bearer")))
                .andExpect(jsonPath("$.role", org.hamcrest.Matchers.is("ADMIN")));
    }

    @Test
    @DisplayName("Deve rejeitar login quando a senha estiver incorreta")
    void deveRejeitarSenhaIncorreta() throws Exception {
        AuthRequest request = new AuthRequest(ADMIN_CPF, "senha_errada");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve rejeitar login quando o CPF não existir")
    void deveRejeitarCpfInexistente() throws Exception {
        AuthRequest request = new AuthRequest("00000000000", "123456");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve rejeitar requisição com campos obrigatórios vazios")
    void deveValidarCamposObrigatorios() throws Exception {
        AuthRequest request = new AuthRequest("", "");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros", notNullValue()));
    }
}
