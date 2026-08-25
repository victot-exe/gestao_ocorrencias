package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.BaseIntegrationTest;
import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.CriarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.EditarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.enums.TipoModalidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OcorrenciaControllerTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Deve criar ocorrência com sucesso quando autenticado como Operador")
    void deveCriarOcorrenciaQuandoOperador() throws Exception {
        CriarOcorrenciaRequest request = new CriarOcorrenciaRequest();
        request.setDescricao("Ocorrência de teste automatizado");
        request.setTipoModalidade(TipoModalidade.ROU);
        request.setDataHoraOcorrencia(LocalDateTime.now().minusHours(1));

        mockMvc.perform(post("/ocorrencias")
                        .header("Authorization", getOperadorToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nomePessoa", is("Operador de Campo")));
    }

    @Test
    @DisplayName("Deve bloquear criação de ocorrência sem token JWT")
    void deveBloquearCriacaoSemToken() throws Exception {
        CriarOcorrenciaRequest request = new CriarOcorrenciaRequest();
        request.setDescricao("Tentativa sem autenticação");
        request.setTipoModalidade(TipoModalidade.ROU);
        request.setDataHoraOcorrencia(LocalDateTime.now());

        mockMvc.perform(post("/ocorrencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve buscar ocorrência por ID com sucesso")
    void deveBuscarOcorrenciaPorId() throws Exception {
        mockMvc.perform(get("/ocorrencias/{id}", OCORRENCIA_1_ID)
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(OCORRENCIA_1_ID)));
    }

    @Test
    @DisplayName("Deve listar minhas ocorrências para o operador logado")
    void deveListarMinhasOcorrencias() throws Exception {
        mockMvc.perform(get("/ocorrencias/minhas")
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("Deve listar todas as ocorrências quando for Gestor")
    void deveListarTodasOcorrenciasQuandoGestor() throws Exception {
        mockMvc.perform(get("/ocorrencias")
                        .header("Authorization", getGestorToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    @DisplayName("Deve bloquear Operador ao tentar listar todas as ocorrências")
    void deveBloquearOperadorAoListarTodasOcorrencias() throws Exception {
        mockMvc.perform(get("/ocorrencias")
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve permitir Operador editar sua própria ocorrência")
    void deveEditarPropriaOcorrencia() throws Exception {
        EditarOcorrenciaRequest request = new EditarOcorrenciaRequest();
        request.setId(OCORRENCIA_1_ID);
        request.setDescricao("Descrição da ocorrência 1 atualizada");
        request.setTipoModalidade(TipoModalidade.ROU);
        request.setDataHoraOcorrencia(LocalDateTime.now().minusHours(2));

        mockMvc.perform(put("/ocorrencias")
                        .header("Authorization", getOperadorToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(OCORRENCIA_1_ID)));
    }

    @Test
    @DisplayName("Deve permitir que Admin delete uma ocorrência")
    void deveDeletarOcorrenciaQuandoAdmin() throws Exception {
        mockMvc.perform(delete("/ocorrencias/{id}", OCORRENCIA_1_ID)
                        .header("Authorization", getAdminToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve bloquear Operador ao tentar deletar uma ocorrência")
    void deveBloquearDelecaoQuandoOperador() throws Exception {
        mockMvc.perform(delete("/ocorrencias/{id}", OCORRENCIA_1_ID)
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve exportar relatório CSV com sucesso quando for Gestor")
    void deveExportarCsvQuandoGestor() throws Exception {
        mockMvc.perform(get("/ocorrencias/exportar/csv")
                        .header("Authorization", getGestorToken()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/csv; charset=UTF-8"))
                .andExpect(header().exists("Content-Disposition"))
                .andExpect(content().string(containsString("ID Ocorrencia;Data Ocorrencia;Modalidade")));
    }

    @Test
    @DisplayName("Deve bloquear Operador ao tentar exportar CSV")
    void deveBloquearExportarCsvQuandoOperador() throws Exception {
        mockMvc.perform(get("/ocorrencias/exportar/csv")
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isForbidden());
    }
}
