package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.CriarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.EditarOcorrenciaRequest;
import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.FiltroOcorrenciaBase;
import com.victot.gestao_ocorrencias.dtos.request.ocorrencias.FiltroOcorrenciaPorPessoa;
import com.victot.gestao_ocorrencias.dtos.response.ocorrencias.OcorrenciaDetalhadoResponse;
import com.victot.gestao_ocorrencias.dtos.response.ocorrencias.OcorrenciaResponseBase;
import com.victot.gestao_ocorrencias.service.OcorrenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController extends BaseController {

    private final OcorrenciaService ocorrenciaService;

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
    public ResponseEntity<OcorrenciaDetalhadoResponse> buscar(@PathVariable String id){
        var response = ocorrenciaService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("por-pessoa")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Page<OcorrenciaResponseBase>> pageableByIdPessoa(@ParameterObject @PageableDefault(size = 15, sort = "dataHoraOcorrencia", direction = Sort.Direction.ASC)Pageable pageable,
                                                                           @ParameterObject FiltroOcorrenciaPorPessoa filtro){

        var response = ocorrenciaService.getPageableWithFilter(filtro, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<Page<OcorrenciaResponseBase>> pageable(@ParameterObject @PageableDefault(size = 15, sort = "dataHoraOcorrencia", direction = Sort.Direction.ASC)Pageable pageable){
        var response = ocorrenciaService.getPageable(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("minhas")
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<Page<OcorrenciaResponseBase>> minhasPageable(@ParameterObject @PageableDefault(size = 15, sort = "dataHoraOcorrencia", direction = Sort.Direction.ASC)Pageable pageable,
                                                                       @ParameterObject FiltroOcorrenciaBase request){
        var idPessoa = getPessoaIdAutenticada();
        var response = ocorrenciaService.getPageableWithFilter(idPessoa, request, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("exportar/csv")
    @PreAuthorize("hasRole('GESTOR')")
    public ResponseEntity<byte[]> exportarCsv() {
        byte[] csvBytes = ocorrenciaService.exportarCsv();
        String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String filename = dataAtual + "_relatorio.csv";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8")
                .body(csvBytes);
    }
    //endregion

    //region PUT
    @PutMapping
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<OcorrenciaResponseBase> edit(@Valid @RequestBody EditarOcorrenciaRequest request){
        request.setPessoaAberturaId(getPessoaIdAutenticada());

        OcorrenciaResponseBase response = ocorrenciaService.edit(request);
        return ResponseEntity.ok(response);
    }
    //endregion

    //region DELETE
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id){
        ocorrenciaService.delete(id);
        return ResponseEntity.noContent().build();
    }
    //endregion
}
