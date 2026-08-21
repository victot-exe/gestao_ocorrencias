package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.dtos.request.tratativaOcorrencias.*;
import com.victot.gestao_ocorrencias.dtos.response.tratativaOcorrencias.*;
import com.victot.gestao_ocorrencias.service.TratativaOcorrenciaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("tratativas")
@RequiredArgsConstructor
public class TratativaOcorrenciaController extends BaseController{
    private final TratativaOcorrenciaService tratativaOcorrenciaService;

    //region POST
    @PostMapping
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<String> create(@Valid @RequestBody CriarTratativaOcorrenciaRequest request){
        request.setPessoaAberturaId(getPessoaIdAutenticada());
        var response = tratativaOcorrenciaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //endregion

    //region GET
    @GetMapping
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<Page<TratativaOcorrenciaBaseResponse>> pageable(@ParameterObject @PageableDefault(size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                                          @RequestParam
                                                                          @NotBlank(message = "O id da ocorrência é obrigatório.")
                                                                          @UUID(message = "Id no formato inválido.")
                                                                          String idOcorrencia){
        var response = tratativaOcorrenciaService.pegeable(pageable, idOcorrencia);
        return ResponseEntity.ok(response);
    }
    //endregion

    //region PUT
    @PutMapping
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<String> editar(@Valid @RequestBody EditarTratativaOcorrenciaRequest request){
        var response = tratativaOcorrenciaService.editar(request);
        return ResponseEntity.ok(response);
    }
    //endregion

    //region delete
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Void> delete(@PathVariable String id){
        tratativaOcorrenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
