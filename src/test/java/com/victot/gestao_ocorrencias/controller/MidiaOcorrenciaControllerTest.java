package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MidiaOcorrenciaControllerTest extends BaseIntegrationTest {

    @Test
    @DisplayName("Deve anexar mídia com sucesso quando autenticado como Operador")
    void deveAnexarMidiaQuandoOperador() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "arquivo",
                "evidencia.png",
                "image/png",
                "conteudo fake de imagem para teste".getBytes()
        );

        mockMvc.perform(multipart("/ocorrencias/{idOcorrencia}/anexos", OCORRENCIA_1_ID)
                        .file(file)
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nomeOriginal", is("evidencia.png")))
                .andExpect(jsonPath("$.contentType", is("image/png")));
    }

    @Test
    @DisplayName("Deve bloquear anexo com formato não suportado")
    void deveBloquearFormatoInvalido() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "arquivo",
                "executavel.exe",
                "application/x-msdownload",
                "conteudo executavel".getBytes()
        );

        mockMvc.perform(multipart("/ocorrencias/{idOcorrencia}/anexos", OCORRENCIA_1_ID)
                        .file(file)
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve listar mídias de uma ocorrência existente")
    void deveListarMidiasDaOcorrencia() throws Exception {
        mockMvc.perform(get("/ocorrencias/{idOcorrencia}/anexos", OCORRENCIA_1_ID)
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(java.util.List.class)));
    }

    @Test
    @DisplayName("Deve retornar 404 ao listar mídias de ocorrência inexistente")
    void deveRetornar404QuandoOcorrenciaInexistente() throws Exception {
        mockMvc.perform(get("/ocorrencias/{idOcorrencia}/anexos", "id-que-nao-existe")
                        .header("Authorization", getOperadorToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve bloquear acesso sem token de autenticação")
    void deveBloquearAcessoSemToken() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "arquivo",
                "evidencia.png",
                "image/png",
                "bytes".getBytes()
        );

        mockMvc.perform(multipart("/ocorrencias/{idOcorrencia}/anexos", OCORRENCIA_1_ID)
                        .file(file))
                .andExpect(status().isForbidden());
    }
}
