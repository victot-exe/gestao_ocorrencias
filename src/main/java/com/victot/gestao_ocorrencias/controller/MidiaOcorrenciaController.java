package com.victot.gestao_ocorrencias.controller;

import com.victot.gestao_ocorrencias.dtos.request.midiaOcorrencia.MidiaOcorrenciaDto;
import com.victot.gestao_ocorrencias.dtos.response.midias.MidiaResponse;
import com.victot.gestao_ocorrencias.service.MidiaOcorrenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("ocorrencias/{idOcorrencia}/anexos")
@RequiredArgsConstructor
public class MidiaOcorrenciaController extends BaseController{

    private final MidiaOcorrenciaService midiaOcorrenciaService;

    //region post
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<MidiaResponse> anexar(
            @PathVariable String idOcorrencia,
            @RequestParam("arquivo") MultipartFile arquivo) {
        var idPessoa = getPessoaIdAutenticada();
        var dto = new MidiaOcorrenciaDto(arquivo, idPessoa, idOcorrencia);

        var response = midiaOcorrenciaService.anexar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //endregion

    //region get
    @GetMapping
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<List<MidiaResponse>> listar(@PathVariable String idOcorrencia) {
        var midias = midiaOcorrenciaService.listarPorOcorrencia(idOcorrencia);
        return ResponseEntity.ok(midias);
    }

    @GetMapping("{idMidia}/download")
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<Resource> download(
            @PathVariable String idOcorrencia,
            @PathVariable String idMidia) {

        var downloadDto = midiaOcorrenciaService.buscarParaDownload(idOcorrencia, idMidia);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(downloadDto.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + downloadDto.nomeOriginal() + "\"")//vou querere mesmo inline? verificar depois quando fizer o front
                .body(downloadDto.resource());
    }
    //endregion

    //region delete
    @DeleteMapping("{idMidia}")
    @PreAuthorize("hasRole('OPERADOR')")
    public ResponseEntity<Void> deletar(
            @PathVariable String idOcorrencia,
            @PathVariable String idMidia) {

        midiaOcorrenciaService.deletar(idOcorrencia, idMidia);
        return ResponseEntity.noContent().build();
    }
    //endregion
}
