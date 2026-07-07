package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.CriarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.response.ocorrencias.OcorrenciaResponseBase;
import com.victot.gestao_ocorrencias.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaController(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @PostMapping
    public ResponseEntity<OcorrenciaResponseBase> criar(@Valid @RequestBody CriarOcorrenciaRequest ocorrencia) {
        var response =  ocorrenciaService.criarOcorrencia(ocorrencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<OcorrenciaResponseBase> buscar(@PathVariable String id){
        var response = ocorrenciaService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("retornar-paginado/{idPessoaAbertura}")
    public ResponseEntity<Page<OcorrenciaResponseBase>> pageableByIdPessoa(@PathVariable String idPessoaAbertura, @ParameterObject @PageableDefault(size = 15, sort = "dataHoraOcorrencia", direction = Sort.Direction.ASC)Pageable pageable){
        var response = ocorrenciaService.getPageableByIdPessoa(idPessoaAbertura, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("retornar-paginado")
    public ResponseEntity<Page<OcorrenciaResponseBase>> pageable(@ParameterObject @PageableDefault(size = 15, sort = "dataHoraOcorrencia", direction = Sort.Direction.ASC)Pageable pageable){
        var response = ocorrenciaService.getPageable(pageable);
        return ResponseEntity.ok(response);
    }
}
