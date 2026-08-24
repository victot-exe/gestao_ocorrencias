package com.victot.gestao_ocorrencias.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
    String salvar(MultipartFile arquivo);
    Resource carregar(String nomeArmazenado);
    void deletar(String nomeArmazenado);
}
