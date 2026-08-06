package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.CriarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.EditarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.response.ocorrencias.OcorrenciaResponseBase;
import com.victot.gestao_ocorrencias.service.OcorrenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController extends BaseController {

    private final OcorrenciaService ocorrenciaService;
//TODO para os paginados adicionar request que contem filtros de data e tal, proximos passos
//TODO trocar o response do get por id para ter tambem a descrição, será usaddo em conjunto com o paginado de tratativas

    //region POST
    @PostMapping
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<OcorrenciaResponseBase> create(@Valid @RequestBody CriarOcorrenciaRequest ocorrencia) {
        ocorrencia.setPessoaAberturaId(getPessoaIdAutenticada());
        var response =  ocorrenciaService.create(ocorrencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //endregion

    //region GET
    @GetMapping("{id}")
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<OcorrenciaResponseBase> buscar(@PathVariable String id){
        var response = ocorrenciaService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("retornar-paginado/{idPessoaAbertura}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Page<OcorrenciaResponseBase>> pageableByIdPessoa(@ParameterObject @PageableDefault(size = 15, sort = "dataHoraOcorrencia", direction = Sort.Direction.ASC)Pageable pageable,
                                                                           @PathVariable String idPessoaAbertura){//POSSO DEIXAR ISSO AQUI ACEITANDO NULL? QUANDO FOR ALGUEM OPERADOR pega do token e ignora o parametro, se for admin ou gestor pega do parametro é uma boa pratica ou é melhor ter dois endpoints?

        var response = ocorrenciaService.getPageableByIdPessoa(idPessoaAbertura, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("retornar-paginado")
    @PreAuthorize("hasRole('GESTOR')")//Somente admin e gestor podem trazer de todos
    public ResponseEntity<Page<OcorrenciaResponseBase>> pageable(@ParameterObject @PageableDefault(size = 15, sort = "dataHoraOcorrencia", direction = Sort.Direction.ASC)Pageable pageable){
        var response = ocorrenciaService.getPageable(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("minhas/retornar-paginado")
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<Page<OcorrenciaResponseBase>> minhasPageble(@ParameterObject @PageableDefault(size = 15, sort = "dataHoraOcorrencia", direction = Sort.Direction.ASC)Pageable pageable){
        var idPessoa = getPessoaIdAutenticada();
        var response = ocorrenciaService.getPageableByIdPessoa(idPessoa, pageable);
        return ResponseEntity.ok(response);
    }
    //endregion

    //region PUT
    @PutMapping
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<OcorrenciaResponseBase> edit(@Valid @RequestBody EditarOcorrenciaRequest request){
        request.setPessoaAberturaId(getPessoaIdAutenticada());

        OcorrenciaResponseBase response = ocorrenciaService.edit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //endregion
}
