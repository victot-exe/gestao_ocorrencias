package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.BaseIntegrationTest;
import com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias.CriarTratativaOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias.EditarTratativaOcorrenciaRequest;
import com.victot.gestao_ocorrencias.enums.StatusOcorrencia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TratativaOcorrenciaControllerTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Deve criar tratativa com sucesso quando Operador")
    void deveCriarTratativaComSucesso() throws Exception {
        CriarTratativaOcorrenciaRequest request = new CriarTratativaOcorrenciaRequest();
        request.setOcorrenciaId(OCORRENCIA_1_ID);
        request.setDescricao("Equipe deslocada para averiguação no local.");
        request.setStatusOcorrencia(StatusOcorrencia.EMR);
        request.setDataHoraTratativa(LocalDateTime.now());

        mockMvc.perform(post("/tratativas")
                        .header("Authorization", getOperadorToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    @Test
    @DisplayName("Deve listar tratativas paginadas de uma ocorrência")
    void deveListarTratativasPaginadas() throws Exception {
        mockMvc.perform(get("/tratativas")
                        .header("Authorization", getOperadorToken())
                        .param("idOcorrencia", OCORRENCIA_2_ID)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @DisplayName("Deve retornar 400 quando idOcorrencia for inválido na listagem")
    void deveRetornar400ParaIdInvalido() throws Exception {
        mockMvc.perform(get("/tratativas")
                        .header("Authorization", getOperadorToken())
                        .param("idOcorrencia", "uuid-invalido-123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros", notNullValue()));
    }

    @Test
    @DisplayName("Deve editar tratativa com sucesso quando Gestor")
    void deveEditarTratativaQuandoGestor() throws Exception {
        EditarTratativaOcorrenciaRequest request = new EditarTratativaOcorrenciaRequest();
        request.setIdTratativaOcorrencia(TRATATIVA_1_ID);
        request.setDescricao("Descrição da tratativa editada pelo gestor");
        request.setDataHoraTratativa(LocalDateTime.now());

        mockMvc.perform(put("/tratativas")
                        .header("Authorization", getGestorToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(TRATATIVA_1_ID));
    }

    @Test
    @DisplayName("Deve bloquear Operador ao tentar editar tratativa")
    void deveBloquearOperadorAoEditarTratativa() throws Exception {
        EditarTratativaOcorrenciaRequest request = new EditarTratativaOcorrenciaRequest();
        request.setIdTratativaOcorrencia(TRATATIVA_1_ID);
        request.setDescricao("Tentativa de edição por operador");
        request.setDataHoraTratativa(LocalDateTime.now());

        mockMvc.perform(put("/tratativas")
                        .header("Authorization", getOperadorToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve deletar tratativa com sucesso quando Gestor")
    void deveDeletarTratativaQuandoGestor() throws Exception {
        mockMvc.perform(delete("/tratativas/{id}", TRATATIVA_1_ID)
                        .header("Authorization", getGestorToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve bloquear Operador ao tentar deletar tratativa")
    void deveBloquearOperadorAoDeletarTratativa() throws Exception {
        mockMvc.perform(delete("/tratativas/{id}", TRATATIVA_1_ID)
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isForbidden());
    }
}
