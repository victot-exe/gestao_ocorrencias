package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.dtos.request.pessoas.*;
import com.victot.gestao_ocorrencias.dtos.response.pessoas.PessoaResponse;
import com.victot.gestao_ocorrencias.enums.PerfilUsuario;
import com.victot.gestao_ocorrencias.service.PessoaService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pessoas")
public class PessoaController extends BaseController{

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PessoaResponse> salvarPessoa(@Valid @RequestBody CriarPessoaRequest request){
        var response = pessoaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    @PreAuthorize("hasRole('GESTOR') or (hasRole('OPERADOR') and #request.id == principal.id)")
    public ResponseEntity<PessoaResponse> editarPessoa(@Valid @RequestBody EditarPessoaRequest request){
        var response = pessoaService.edit(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('GESTOR') or (hasRole('OPERADOR') and #id == principal.id)")
    public ResponseEntity<PessoaResponse> getPessoa(@PathVariable String id){
        var response = pessoaService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("retornar-paginado")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Page<PessoaResponse>> retornarPaginado(@ParameterObject @PageableDefault(size = 15, sort = "nome", direction = Sort.Direction.ASC) Pageable paginationRequest){
        var response = pessoaService.getPaginado(paginationRequest);
        return ResponseEntity.ok(response);
    }
}
