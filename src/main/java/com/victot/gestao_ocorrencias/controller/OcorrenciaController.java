package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.dtos.request.CriarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.entity.Ocorrencia;
import com.victot.gestao_ocorrencias.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @PostMapping
    public ResponseEntity<Ocorrencia> criar(@Valid @RequestBody CriarOcorrenciaRequest ocorrencia) {
        var response =  ocorrenciaService.criarOcorrencia(ocorrencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
