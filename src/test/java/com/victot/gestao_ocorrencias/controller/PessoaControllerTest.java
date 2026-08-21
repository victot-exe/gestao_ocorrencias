package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.BaseIntegrationTest;
import com.victot.gestao_ocorrencias.dtos.request.pessoas.CriarPessoaRequest;
import com.victot.gestao_ocorrencias.dtos.request.pessoas.EditarPessoaRequest;
import com.victot.gestao_ocorrencias.enums.PerfilUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PessoaControllerTest extends BaseIntegrationTest {

    // CPFs válidos matematicamente segundo o algoritmo do Ministério da Fazenda
    private static final String CPF_VALIDO_1 = "52998224725";
    private static final String CPF_VALIDO_2 = "12345678909";
    private static final String CPF_VALIDO_3 = "98765432100";

    @Test
    @DisplayName("Deve criar uma nova pessoa com sucesso")
    void deveCriarPessoaComSucesso() throws Exception {
        CriarPessoaRequest request = new CriarPessoaRequest();
        request.setNome("Novo Usuário");
        request.setCpf(CPF_VALIDO_1);
        request.setPerfilUsuario(PerfilUsuario.OPERADOR);
        request.setPassword("123456");
        request.setConfirmPassword("123456");

        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nome", is("Novo Usuário")))
                .andExpect(jsonPath("$.cpf", is(CPF_VALIDO_1)));
    }

    @Test
    @DisplayName("Deve rejeitar criação quando as senhas forem diferentes")
    void deveRejeitarSenhasDiferentes() throws Exception {
        CriarPessoaRequest request = new CriarPessoaRequest();
        request.setNome("Usuário Inválido");
        request.setCpf(CPF_VALIDO_1);
        request.setPerfilUsuario(PerfilUsuario.OPERADOR);
        request.setPassword("123456");
        request.setConfirmPassword("654321");

        mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros", notNullValue()));
    }

    @Test
    @DisplayName("Deve buscar pessoa por ID quando for Gestor")
    void deveBuscarPessoaPorIdQuandoGestor() throws Exception {
        mockMvc.perform(get("/pessoas/{id}", OPERADOR_ID)
                        .header("Authorization", getGestorToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(OPERADOR_ID)))
                .andExpect(jsonPath("$.nome", is("Operador de Campo")));
    }

    @Test
    @DisplayName("Deve permitir que o próprio Operador busque seu perfil por ID")
    void deveBuscarProprioPerfilPorId() throws Exception {
        mockMvc.perform(get("/pessoas/{id}", OPERADOR_ID)
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(OPERADOR_ID)));
    }

    @Test
    @DisplayName("Deve bloquear Operador tentando ver o perfil de outro usuário por ID")
    void deveBloquearOperadorAoVerOutroPerfil() throws Exception {
        mockMvc.perform(get("/pessoas/{id}", GESTOR_ID)
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve retornar 404 quando buscar pessoa inexistente")
    void deveRetornar404ParaPessoaInexistente() throws Exception {
        String idInexistente = "00000000-0000-0000-0000-000000000000";

        mockMvc.perform(get("/pessoas/{id}", idInexistente)
                        .header("Authorization", getAdminToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar lista paginada de pessoas quando for Gestor")
    void deveListarPessoasPaginadoQuandoGestor() throws Exception {
        mockMvc.perform(get("/pessoas/retornar-paginado")
                        .header("Authorization", getGestorToken())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("Deve bloquear Operador ao tentar listar pessoas paginado")
    void deveBloquearOperadorAoListarPessoas() throws Exception {
        mockMvc.perform(get("/pessoas/retornar-paginado")
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve permitir Operador editar seu próprio perfil")
    void devePermitirOperadorEditarProprioPerfil() throws Exception {
        EditarPessoaRequest request = new EditarPessoaRequest();
        request.setId(OPERADOR_ID);
        request.setNome("Operador Nome Atualizado");
        request.setCpf(CPF_VALIDO_2);
        request.setPerfilUsuario(PerfilUsuario.OPERADOR);
        request.setPassword("123456");
        request.setConfirmPassword("123456");

        mockMvc.perform(put("/pessoas")
                        .header("Authorization", getOperadorToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Operador Nome Atualizado")));
    }

    @Test
    @DisplayName("Deve bloquear Operador ao tentar editar perfil de outro usuário")
    void deveBloquearOperadorAoEditarOutroPerfil() throws Exception {
        EditarPessoaRequest request = new EditarPessoaRequest();
        request.setId(GESTOR_ID);
        request.setNome("Tentativa de Hack");
        request.setCpf(CPF_VALIDO_3);
        request.setPerfilUsuario(PerfilUsuario.GESTOR);
        request.setPassword("123456");
        request.setConfirmPassword("123456");

        mockMvc.perform(put("/pessoas")
                        .header("Authorization", getOperadorToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}
