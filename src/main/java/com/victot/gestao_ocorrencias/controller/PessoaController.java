package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.dtos.request.pessoas.*;
import com.victot.gestao_ocorrencias.dtos.response.pessoas.PessoaResponse;
import com.victot.gestao_ocorrencias.service.PessoaService;
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
@RequestMapping("pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> salvarPessoa(@Valid @RequestBody CriarPessoaRequest request){
        var response = pessoaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<PessoaResponse> editarPessoa(@Valid @RequestBody EditarPessoaRequest request){
        var response = pessoaService.edit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("{id}")
    public ResponseEntity<PessoaResponse> getPessoa(@PathVariable String id){
        var response = pessoaService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("retornar-paginado")
    public ResponseEntity<Page<PessoaResponse>> retornarPaginado(@ParameterObject @PageableDefault(size = 15, sort = "nome", direction = Sort.Direction.ASC) Pageable paginationRequest){
        var response = pessoaService.getPaginado(paginationRequest);
        return ResponseEntity.ok(response);
    }


}
