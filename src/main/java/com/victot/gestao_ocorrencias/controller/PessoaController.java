package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.dtos.request.CriarPessoaRequest;
import com.victot.gestao_ocorrencias.entity.Pessoa;
import com.victot.gestao_ocorrencias.service.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<Pessoa> salvarPessoa(@RequestBody CriarPessoaRequest request){
        var response = pessoaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
